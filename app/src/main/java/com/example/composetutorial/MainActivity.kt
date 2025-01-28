package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.foundation.border
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTutorialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatApp()
                }
            }
        }
    }
}

@Composable
fun ChatApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("chat/{personId}") { backStackEntry ->
            val personId = backStackEntry.arguments?.getString("personId")
            val person = SampleData.people.first { it.id == personId }
            ChatScreen(navController, person)
        }
        composable("profile/{personId}") { backStackEntry ->
            val personId = backStackEntry.arguments?.getString("personId")
            val person = SampleData.people.find { it.id == personId }
            person?.let {
                ProfileScreen(navController, it)
            }
        }
    }
}


@Composable
fun PersonItem(person: Person, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(person.profilePicture),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = person.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = person.status,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun MessageCard(msg: Message, isUserMessage: Boolean) {
    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        if (!isUserMessage) {
            Image(
                painter = painterResource(msg.profilePicture),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        var isExpanded by remember { mutableStateOf(false) }
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            if (!isUserMessage) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = if (isUserMessage) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isUserMessage) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
            }
        }
        if (isUserMessage) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(msg.profilePicture),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
        }
    }
}

@Composable
fun ChatScreen(navController: NavController, person: Person) {
    val messages = remember {
        mutableStateListOf(
            *SampleData.conversationSample
                .filter { it.id == person.id }
                .reversed()
                .toTypedArray()
        )
    }
    var newMessage by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(messages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = person.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                navController.navigate("profile/${person.id}") // Navigate to profile screen
            }) {
                Icon(Icons.Default.Person, contentDescription = "Profile")
            }
        }

        // Chat Messages
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = scrollState,
            reverseLayout = true
        ) {
            items(messages) { message ->
                val isUserMessage = message.author == "You"
                MessageCard(message, isUserMessage)
            }
        }

        // Message Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (newMessage.isNotBlank()) {
                        val newMsg = Message(
                            id = person.id,
                            author = "You",
                            body = newMessage,
                            profilePicture = R.drawable.noman
                        )
                        messages.add(0, newMsg)
                        newMessage = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val people = SampleData.people
    var showMenu by remember { mutableStateOf(false) }

    // Drawer State (Initially closed)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Handle the opening and closing of the drawer
    LaunchedEffect(showMenu) {
        if (showMenu) {
            drawerState.open() // Open drawer when showMenu is true
        } else {
            drawerState.close() // Close drawer when showMenu is false
        }
    }

    // Drawer Content
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                userName = "Md Noman",
                userEmail = "mdnoman24@student.oulu.fi",
                userProfilePicture = painterResource(id = R.drawable.noman),
                onClose = { showMenu = false }, // Close drawer on close
                onEditProfile = { /* Edit profile functionality */ },
                onSettings = { /* Open settings functionality */ },
                onLogout = {
                    // Handle logout functionality
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                navController = navController
            )
        }
    ) {
        // Main Content of the Home Screen
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App Name
                Text(
                    text = "MdnChat",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )

                // Search Button
                IconButton(onClick = { /* Handle search functionality */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }

                // Menu Button (3 dots)
                IconButton(onClick = {
                    showMenu = !showMenu // Toggle menu visibility
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }

            // List of People
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(people) { person ->
                    PersonItem(person = person, onClick = {
                        navController.navigate("chat/${person.id}")
                    })
                }
            }

            // Bottom Navigation Bar
            BottomNavigationBar(navController)
        }
    }
}
@Composable
fun DrawerContent(
    userName: String,
    userEmail: String,
    userProfilePicture: Painter,
    onClose: () -> Unit,
    onEditProfile: () -> Unit,
    onSettings: () -> Unit,
    onLogout: () -> Unit,
    navController: NavController // Added NavController for navigation
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
             // Solid background for expanded drawer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // User Profile Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = userProfilePicture,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Menu Options
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onEditProfile,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile")
            }

            TextButton(
                onClick = onSettings,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Settings")
            }

            Spacer(modifier = Modifier.weight(1f)) // Push remaining items to the bottom

            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            // Logout Button with Navigation
            Button(
                onClick = {
                    onLogout()
                    navController.navigate("home") { // Navigate to home screen
                        popUpTo("home") { inclusive = true } // Clear navigation stack
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Clear, contentDescription = "Logout")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Close Drawer Button with Navigation
            Button(
                onClick = {
                    onClose()
                    navController.navigate("home") // Navigate to home screen
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close Menu")
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Chats", Icons.Default.MailOutline, "chats"),
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Calls", Icons.Default.Call, "calls")
    )

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, person: Person) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(person.profilePicture),
            contentDescription = "${person.name}'s profile picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = person.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Status: ${person.status}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Chat")
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)
