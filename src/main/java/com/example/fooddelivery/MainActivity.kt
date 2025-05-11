package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDeliveryApp()
        }
    }
}

@Composable
fun FoodDeliveryApp() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val cartItems = remember { mutableStateListOf<FoodItem>() }

    BackgroundContainer {
        if (isLoggedIn) {
            MainAppContent(cartItems)
        } else {
            LoginScreen { isLoggedIn = true }
        }
    }
}

@Composable
fun BackgroundContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // ✅ Let child content manage its own scroll
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content()
        }
    }
}


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(Color.White.copy(alpha = 0.8f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onLoginSuccess() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun MainAppContent(cartItems: MutableList<FoodItem>) {
    var selectedScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedScreen == Screen.Home,
                    onClick = { selectedScreen = Screen.Home },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedScreen == Screen.Cart,
                    onClick = { selectedScreen = Screen.Cart },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                    label = { Text("Cart") }
                )
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        BackgroundContainer {
            Box(modifier = Modifier.padding(paddingValues)) {
                when (selectedScreen) {
                    Screen.Home -> HomeScreen(cartItems)
                    Screen.Cart -> CartScreen(cartItems)
                }
            }
        }
    }
}

enum class Screen {
    Home, Cart
}

data class FoodItem(val name: String, val price: String, val emoji: String, val category: String)

val sampleFoodItems = listOf(
    FoodItem("Apple", "$13.43", "🍎", "Fruits"),
    FoodItem("Banana", "$17.66", "🍌", "Fruits"),
    FoodItem("Orange", "$2.13", "🍊", "Fruits"),
    FoodItem("Grapes", "$19.43", "🍇", "Fruits"),
    FoodItem("Pineapple", "$18.43", "🍍", "Fruits"),
    FoodItem("Mango", "$14.76", "🥭", "Fruits"),
    FoodItem("Strawberry", "$6.31", "🍓", "Fruits"),
    FoodItem("Blueberry", "$10.28", "🫐", "Fruits"),
    FoodItem("Pear", "$2.25", "🍐", "Fruits"),
    FoodItem("Peach", "$10.4", "🍑", "Fruits"),
    FoodItem("Carrot", "$3.15", "🥕", "Vegetables"),
    FoodItem("Broccoli", "$18.34", "🥦", "Vegetables"),
    FoodItem("Spinach", "$3.84", "🥬", "Vegetables"),
    FoodItem("Potato", "$2.17", "🥔", "Vegetables"),
    FoodItem("Tomato", "$10.55", "🍅", "Vegetables"),
    FoodItem("Cucumber", "$12.55", "🥒", "Vegetables"),
    FoodItem("Onion", "$5.93", "🧅", "Vegetables"),
    FoodItem("Pepper", "$14.91", "🌶️", "Vegetables"),
    FoodItem("Garlic", "$13.39", "🧄", "Vegetables"),
    FoodItem("Corn", "$2.34", "🌽", "Vegetables"),
    FoodItem("Burger", "$9.48", "🍔", "Junk"),
    FoodItem("Fries", "$12.47", "🍟", "Junk"),
    FoodItem("Pizza", "$5.46", "🍕", "Junk"),
    FoodItem("Hot Dog", "$8.76", "🌭", "Junk"),
    FoodItem("Nuggets", "$6.13", "🍗", "Junk"),
    FoodItem("Donut", "$17.53", "🍩", "Junk"),
    FoodItem("Chips", "$14.18", "🍿", "Junk"),
    FoodItem("Taco", "$18.5", "🌮", "Junk"),
    FoodItem("Burrito", "$13.46", "🌯", "Junk"),
    FoodItem("Soda", "$8.74", "🥤", "Junk"),
    FoodItem("Chocolate Cake", "$15.48", "🍰", "Cakes"),
    FoodItem("Vanilla Cake", "$17.43", "🍰", "Cakes"),
    FoodItem("Cheesecake", "$2.4", "🍰", "Cakes"),
    FoodItem("Cupcake", "$10.47", "🧁", "Cakes"),
    FoodItem("Brownie", "$5.91", "🍫", "Cakes"),
    FoodItem("Macaron", "$9.68", "🎂", "Cakes"),
    FoodItem("Muffin", "$15.66", "🧁", "Cakes"),
    FoodItem("Swiss Roll", "$14.64", "🍥", "Cakes"),
    FoodItem("Red Velvet", "$15.8", "🍰", "Cakes"),
    FoodItem("Carrot Cake", "$13.14", "🥕", "Cakes"),
    FoodItem("Coffee", "$7.96", "☕", "Drinks"),
    FoodItem("Tea", "$15.2", "🍵", "Drinks"),
    FoodItem("Smoothie", "$14.04", "🥤", "Drinks"),
    FoodItem("Milkshake", "$13.68", "🥤", "Drinks"),
    FoodItem("Cola", "$17.64", "🥤", "Drinks"),
    FoodItem("Lemonade", "$17.16", "🍋", "Drinks"),
    FoodItem("Juice", "$2.18", "🧃", "Drinks"),
    FoodItem("Iced Tea", "$19.02", "🥤", "Drinks"),
    FoodItem("Water", "$18.72", "💧", "Drinks"),
    FoodItem("Energy Drink", "$5.17", "⚡", "Drinks"),
    FoodItem("Apple Special 1", "$11.91", "🍎", "Fruits"),
    FoodItem("Donut Special 2", "$7.58", "🍩", "Junk"),
    FoodItem("Hot Dog Special 3", "$3.28", "🌭", "Junk"),
    FoodItem("Vanilla Cake Special 4", "$18.01", "🍰", "Cakes"),
    FoodItem("Strawberry Special 5", "$6.67", "🍓", "Fruits"),
    FoodItem("Muffin Special 6", "$6.74", "🧁", "Cakes"),
    FoodItem("Cupcake Special 7", "$2.91", "🧁", "Cakes"),
    FoodItem("Pizza Special 8", "$3.34", "🍕", "Junk"),
    FoodItem("Fries Special 9", "$2.8", "🍟", "Junk"),
    FoodItem("Spinach Special 10", "$18.27", "🥬", "Vegetables"),
    FoodItem("Potato Special 11", "$7.62", "🥔", "Vegetables"),
    FoodItem("Coffee Special 12", "$13.82", "☕", "Drinks"),
    FoodItem("Nuggets Special 13", "$18.64", "🍗", "Junk"),
    FoodItem("Cola Special 14", "$8.43", "🥤", "Drinks"),
    FoodItem("Energy Drink Special 15", "$14.98", "⚡", "Drinks"),
    FoodItem("Garlic Special 16", "$5.42", "🧄", "Vegetables"),
    FoodItem("Tomato Special 17", "$3.87", "🍅", "Vegetables"),
    FoodItem("Macaron Special 18", "$18.6", "🎂", "Cakes"),
    FoodItem("Smoothie Special 19", "$10.36", "🥤", "Drinks"),
    FoodItem("Taco Special 20", "$8.23", "🌮", "Junk"),
    FoodItem("Mango Special 21", "$13.76", "🥭", "Fruits"),
    FoodItem("Onion Special 22", "$3.59", "🧅", "Vegetables"),
    FoodItem("Burrito Special 23", "$11.31", "🌯", "Junk"),
    FoodItem("Red Velvet Special 24", "$18.96", "🍰", "Cakes"),
    FoodItem("Tea Special 25", "$3.2", "🍵", "Drinks"),
    FoodItem("Blueberry Special 26", "$3.38", "🫐", "Fruits"),
    FoodItem("Cucumber Special 27", "$5.23", "🥒", "Vegetables"),
    FoodItem("Water Special 28", "$6.07", "💧", "Drinks"),
    FoodItem("Corn Special 29", "$6.9", "🌽", "Vegetables"),
    FoodItem("Lemonade Special 30", "$2.8", "🍋", "Drinks"),
    FoodItem("Broccoli Special 31", "$7.75", "🥦", "Vegetables"),
    FoodItem("Pear Special 32", "$10.48", "🍐", "Fruits"),
    FoodItem("Pepper Special 33", "$6.84", "🌶️", "Vegetables"),
    FoodItem("Chips Special 34", "$14.74", "🍿", "Junk"),
    FoodItem("Swiss Roll Special 35", "$8.1", "🍥", "Cakes"),
    FoodItem("Juice Special 36", "$5.36", "🧃", "Drinks"),
    FoodItem("Brownie Special 37", "$2.33", "🍫", "Cakes"),
    FoodItem("Grapes Special 38", "$8.41", "🍇", "Fruits"),
    FoodItem("Cheesecake Special 39", "$18.69", "🍰", "Cakes"),
    FoodItem("Peach Special 40", "$18.71", "🍑", "Fruits"),
    FoodItem("Milkshake Special 41", "$4.1", "🥤", "Drinks"),
    FoodItem("Carrot Special 42", "$5.42", "🥕", "Vegetables"),
    FoodItem("Iced Tea Special 43", "$17.71", "🥤", "Drinks"),
    FoodItem("Chocolate Cake Special 44", "$17.97", "🍰", "Cakes"),
    FoodItem("Banana Special 45", "$11.65", "🍌", "Fruits"),
    FoodItem("Carrot Cake Special 46", "$13.68", "🥕", "Cakes"),
    FoodItem("Onion Special 47", "$5.73", "🧅", "Vegetables"),
    FoodItem("Tomato Special 48", "$10.21", "🍅", "Vegetables"),
    FoodItem("Burger Special 49", "$13.15", "🍔", "Junk"),
    FoodItem("Strawberry Special 50", "$4.97", "🍓", "Fruits")
)


@Composable
fun HomeScreen(cartItems: MutableList<FoodItem>) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems = sampleFoodItems.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    val groupedItems = filteredItems.groupBy { it.category }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.8f))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search food") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }

        // Scrollable List of Food Groups
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            groupedItems.forEach { (category, items) ->
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                items(items) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("${item.emoji} ${item.name}", fontSize = 20.sp, color = Color.Black)
                                Text(item.price, color = MaterialTheme.colorScheme.primary)
                            }
                            Button(onClick = { cartItems.add(item) }) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CartScreen(cartItems: MutableList<FoodItem>) {
    var orderPlaced by remember { mutableStateOf(false) }
    val quantities = remember { mutableStateListOf(*IntArray(cartItems.size) { 1 }.toTypedArray()) }

    // Ensure quantities matches cart size
    while (quantities.size < cartItems.size) quantities.add(1)

    if (cartItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cart is empty!", color = Color.Black)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Cart",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.7f))
                .padding(8.dp)
        )

        // Scrollable content including list and totals
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
            items(cartItems.indices.toList()) { index ->
                val item = cartItems[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${item.emoji} ${item.name}", color = Color.Black)
                            Text(item.price, color = Color.Black)
                        }
                        Row {
                            Button(onClick = {
                                if (quantities[index] > 1) quantities[index]--
                            }) { Text("-") }

                            Text(
                                quantities[index].toString(),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Button(onClick = {
                                quantities[index]++
                            }) { Text("+") }
                        }
                    }
                }
            }
        }

        // Total and Order button
        val total = cartItems.indices.sumOf {
            cartItems[it].price.removePrefix("$").toDoubleOrNull()?.times(quantities[it]) ?: 0.0
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total: $${String.format("%.2f", total)}", fontSize = 20.sp, color = Color.Black)

            Button(
                onClick = { orderPlaced = true },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Place Order")
            }
        }

        if (orderPlaced) {
            OrderConfirmationScreen(cartItems)
        }
    }
}

@Composable
fun OrderConfirmationScreen(cartItems: List<FoodItem>) {
    val deliveryTime = remember { mutableIntStateOf(Random.nextInt(10, 60)) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        BackgroundContainer {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Order Placed Successfully!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Your order will be delivered in ${deliveryTime.intValue} minutes.",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Items:", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                cartItems.forEach {
                    Text(
                        "${it.emoji} ${it.name} - ${it.price}",
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

