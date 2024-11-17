#UnityAPI

This project is a Spring Boot application that exposes RESTful APIs for managing users and weapons. It can be seamlessly integrated with a Unity project to interact with user and weapon data, making it suitable for game development or any application that requires real-time data handling.

#Features:

Create, list, update, and delete users and weapons.
Manage user and weapon attributes such as damage, cooldown, experience, and gold.

#Integration with Unity:

This Spring Boot API works with a Unity project, allowing you to retrieve and manipulate data (such as users and weapons) from the Unity game interface. The Unity project interacts with this API to retrieve information about users or weapons, update weapon statistics, or delete existing entries. You can also access the Unity Project with this link
https://github.com/Muhammet933321/Unity_SpringBoot_API.git

#API Examples:

Get all users: GET /api/users
Get all weapons: GET /api/weapons
Create a user: POST /api/users
Update weapon: PUT /api/weapons/{id}
You can integrate these API calls in Unity to manage your game’s user data, weapons, and attributes dynamically.
