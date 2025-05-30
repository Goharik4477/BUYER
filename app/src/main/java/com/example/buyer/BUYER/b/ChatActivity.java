package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.BUYER.b.adapters.ChatAdapter;
import com.example.buyer.BUYER.b.models.ChatMessage;
import com.example.buyer.BUYER.b.models.User;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private TextView textRating;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private User receiverUser;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private Bitmap selectedImageBitmap;
    private Boolean isReceiverAvailable = false;
    private String conversionId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);


        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        loadReceiverDetails();
        setListeners();

        init();
        listenMessages();

        textRating = findViewById(R.id.textRating);
        loadReceiverRating();
        textRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, "You can rate the user after the transaction is completed using the button Info", Toast.LENGTH_SHORT).show();
            }
        });
        binding.imageInfo.setEnabled(false);
        binding.imageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, RatingActivity.class);
                intent.putExtra("userId", receiverUser.id);
                intent.putExtra("userName", receiverUser.name);
                startActivity(intent);
            }
        });

    }


    private void loadReceiverRating() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(receiverUser.id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long totalRating = documentSnapshot.getLong("totalRating");
                        Long ratingCount = documentSnapshot.getLong("ratingCount");

                        if (totalRating != null && ratingCount != null && ratingCount > 0) {
                            double average = (double) totalRating / ratingCount;
                            textRating.setText(String.format(Locale.getDefault(), "%.1f ★", average));
                        } else {
                            textRating.setText("No rating");
                        }
                    } else {
                        textRating.setText("No rating");
                    }
                })
                .addOnFailureListener(e -> {
                    textRating.setText("Rating error");
                });
    }

    private void init() {
        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages,
                getBitmapFromEncodedString(receiverUser.image),
                preferenceManager.getString(Constants.KEY_USER_ID));

        binding.chatRecyclerView.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
    }


    private void sendMessage() {
        String messageText = binding.inputMessage.getText().toString().trim();

        if (messageText.isEmpty()) {
            Toast.makeText(this, "Enter your message", Toast.LENGTH_SHORT).show();
            return;
        }

        if (receiverUser == null || receiverUser.id == null) {
            Toast.makeText(this, "Error: Recipient not found", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, messageText);
        message.put(Constants.KEY_TIMESTAMP, new Date());


        database.collection(Constants.KEY_COLLECTION_CHAT)
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    if (conversionId != null) {
                        updateConversion(messageText);
                    } else {
                        HashMap<String, Object> conversion = new HashMap<>();
                        conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
                        conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_NAME));
                        conversion.put(Constants.KEY_SENDER_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));
                        conversion.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
                        conversion.put(Constants.KEY_RECEIVER_NAME, receiverUser.name);
                        conversion.put(Constants.KEY_RECEIVER_IMAGE, receiverUser.image);
                        conversion.put(Constants.KEY_LAST_MESSAGE, messageText);
                        conversion.put(Constants.KEY_TIMESTAMP, new Date());
                        addConversion(conversion);
                    }
                    binding.inputMessage.setText(null);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error sending: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ChatActivity", "Error sending: ", e);
                });
    }
    private void listenAvailabilityOfReceiver() {
        database.collection(Constants.KEY_COLLECTION_USERS).document(
                receiverUser.id
        ).addSnapshotListener(ChatActivity.this, (value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABILITY) != null) {
                    int availability = Objects.requireNonNull(
                            value.getLong(Constants.KEY_AVAILABILITY)
                    ).intValue();
                    isReceiverAvailable = availability == 1;
                }
                receiverUser.token = value.getString(Constants.KEY_FCM_TOKEN);
            }
            if (isReceiverAvailable) {
                binding.textAvailability.setVisibility(View.VISIBLE);
            } else {
                binding.textAvailability.setVisibility(View.GONE);
            }

        });

    }

    private void listenMessages() {
        database.collection(Constants.KEY_COLLECTION_CHAT).whereEqualTo(Constants.KEY_SENDER_ID,
                        preferenceManager.getString(Constants.KEY_USER_ID)).
                whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.id)
                .addSnapshotListener(eventListener);

        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);

    }



    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.image = documentChange.getDocument().getString("image");
                    chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(chatMessage);
                }
            }

            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeChanged(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }

            if (chatMessages.size() >= 2) {
                binding.imageInfo.setEnabled(true);
            }
            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
        if (conversionId == null) {
            checkForConversion();
        }
    };


    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void loadReceiverDetails() {
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);

        binding.textName.setText(receiverUser.name);
    }

    private void setListeners() {
        binding.ImageBack.setOnClickListener(v -> onBackPressed());
        binding.LayoutSend.setOnClickListener(v -> sendMessage());
        binding.imageSendPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void addConversion(HashMap<String, Object> conversion) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .add(conversion)
                .addOnSuccessListener(documentReference -> conversionId = documentReference.getId());
    }

    private void updateConversion(String message) {
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_CONVERSATION).document(conversionId);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message,
                Constants.KEY_TIMESTAMP, new Date());
    }

    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(
                    preferenceManager.getString(Constants.KEY_USER_ID), receiverUser.id
            );
            checkForConversionRemotely(
                    receiverUser.id, preferenceManager.getString(Constants.KEY_USER_ID)
            );
        }
    }

    private void checkForConversionRemotely(String senderId, String receiverId) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .whereEqualTo(Constants.KEY_SENDER_ID, senderId)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverId).get().addOnCompleteListener(conversionOnCompeteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionOnCompeteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        listenAvailabilityOfReceiver();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                selectedImageBitmap = BitmapFactory.decodeStream(inputStream);

                sendImage(selectedImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendImage(Bitmap bitmap) {
        String encodedImage = encodeImage(bitmap);

        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, "");
        message.put("image", encodedImage);
        message.put(Constants.KEY_TIMESTAMP, new Date());

        database.collection(Constants.KEY_COLLECTION_CHAT)
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    if (conversionId != null) {
                        updateConversion("[Image]");
                    } else {
                        HashMap<String, Object> conversion = new HashMap<>();
                        conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
                        conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_NAME));
                        conversion.put(Constants.KEY_SENDER_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));
                        conversion.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
                        conversion.put(Constants.KEY_RECEIVER_NAME, receiverUser.name);
                        conversion.put(Constants.KEY_RECEIVER_IMAGE, receiverUser.image);
                        conversion.put(Constants.KEY_LAST_MESSAGE, "[Image]");
                        conversion.put(Constants.KEY_TIMESTAMP, new Date());
                        addConversion(conversion);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Image send failed", Toast.LENGTH_SHORT).show());
    }

    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}





