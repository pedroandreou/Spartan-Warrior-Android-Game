## 📰 Description
The main idea of my game is based on Greek mythology. The hero of my game is a Spartan warrior whose mission is to reach as many levels as his can. This can happen only by having in his possession a key which will be used to open the door for reaching the next level. However, the way to glory will not be that easy as hazards will appear through his way and if he touches any of them, he will firstly lose health and then dies with the game terminating. For each soup that he collects, we can hear him eating and the health increases. The game has got many sounds and background music which catch the player’s attention and enhance user experience

<div align="center">
  <img src="https://github.com/user-attachments/assets/61f26f35-7832-4911-91cd-7d0fbcb7d0df" alt="Spartan Warrior Game Screenshot" width="500"/>
</div>

## 🎮 Gameplay
The game consists of four activities:
- **Main Menu** with three options:
  - "Start": Begins the game
  - "High Score": View the leaderboard
  - "Exit": Leave the game
- Upon game over, players submit credentials which are stored in Firebase along with their score
- Players return to the main menu after submission

## 🏗️ Technical Architecture
<div align="center">
  <img src="https://github.com/user-attachments/assets/6a4212cf-ff8f-4bc4-8906-9d49ea57afae" alt="Game Architecture Diagram" width="700"/>
</div>

The game is built using robust OOP principles:
- **GameObjectMgr**: Central manager controlling all game objects
  - Manages object pools
  - Handles JSON file loading
  - Created new instance per level
- **Class Hierarchy**:
  - AbstractGameObject (Base class)
    - GameObject (Adds collectability property)
      - Pawn (Adds health, speed, player status, movement capabilities)

## 🛠 Initialization & Setup
#### Clone the repository  
    git clone https://github.com/pedroandreou/Spartan-Warrior-Android-Game.git

## 🚀 Building and Running
### Options:
1. Install my [APK](https://github.com/pedroandreou/Spartan-Warrior-Android-Game/tree/main/APK) on your device by following these [instructions](https://www.lifewire.com/install-apk-on-android-4177185)
2. Run the Project to play my game in an emulator  
3. [Re-generate the APK](https://developer.android.com/studio/run)  

## Author  
<a href="https://www.linkedin.com/in/petrosandreou80/">
  <img align="center" src="https://img.shields.io/badge/Petros LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" />
</a>
