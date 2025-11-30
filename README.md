# 🍽️ Lada – Social Cooking & Recipe Sharing App  
**Android Application • Kotlin Native**

Lada is a modern Android app that allows users to **share recipes**, **upload cooking videos**, and **discover dishes** created by the community.  
The main goal is to create a simple and enjoyable social space for food lovers.

---

## ✨ Features

### 🔥 Recipe Sharing  
Users can add:
- Recipe title  
- Ingredients  
- Preparation steps  
- Photos  
- Optional cooking video  

### 🎥 Video Tutorials  
Built-in video player (ExoPlayer).

### 🧑‍🍳 Explore Recipes  
Filter and browse recipes by:
- Trending  
- Most Liked  
- Recent  
- Categories  

### ❤️ Social Interaction  
Users can:
- Like  
- Comment  
- Save recipes  
- Share with others  

### 👤 User Profiles  
Each user has:
- Avatar  
- Bio  
- Their own recipes  
- Saved recipes  

### 🔍 Search  
Search by:
- Name  
- Ingredient  
- Category  
- Author  

---

# 🛠️ Tech Stack

### Android (Kotlin)
- Kotlin  
- Jetpack Compose  
- ViewModel & StateFlow  
- Coroutines  
- Navigation Component  
- Coil (images)  
- ExoPlayer (videos)  

### Backend Options
- **Firebase (Auth, Firestore, Storage)**  
**or**  
- **Django REST API**

---

# 📁 Project Structure

app/
├─ data/
│ ├─ models/
│ ├─ repository/
│ └─ remote/
│
├─ ui/
│ ├─ home/
│ ├─ recipe/
│ ├─ add/
│ ├─ profile/
│ └─ components/
│
├─ viewmodel/
│
├─ utils/
│
└─ di/
