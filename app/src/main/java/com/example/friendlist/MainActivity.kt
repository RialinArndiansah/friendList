package com.example.friendlist

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter



// Data class untuk teman
data class Friend(
    val id: Int,
    val photo: Int,
    val name: String,
    val email: String,
    val address: String
)

// Contoh data 20 teman
val friendsData = listOf(
    Friend(1, R.drawable.image1, "Ahmad Nugraha", "Ahmad.Nugraha@gmail.com", "Jalan Utama No. 123, Jakarta"),
    Friend(2, R.drawable.image2, "Angga Tirta Aditia", "Angga.Tirta.Aditia@gmail.com", "Jalan Samping No. 456, Bandung"),
    Friend(3, R.drawable.image3, "Apri Supandi Pasaribu", "Apri.Supandi.Pasaribu@gmail.com", "Jalan Tinggi No. 789, Surabaya"),
    Friend(5, R.drawable.image5, "Dendi Setiawan", "Dendi.Setiawan@gmail.com", "Jalan Tengah No. 202, Semarang"),
    Friend(10, R.drawable.image10, "Elfarina Fadma Sucitra", "Elfarina.Fadma.Sucitra@gmail.com", "Jalan Ketiga No. 707, Depok"),
    Friend(11, R.drawable.image11, "Hariri Hasnul Habib Rambe", "Hariri.Hasnul.Habib.Rambe@gmail.com", "Jalan Keempat No. 808, Bekasi"),
    Friend(7, R.drawable.image7, "Haziel Wisma Attar", "Haziel.Wisma.Attar@gmail.com", "Jalan Sisi No. 404, Yogyakarta"),
    Friend(9, R.drawable.image9, "Jelita Aurelia", "Jelita.Aurelia@gmail.com", "Jalan Kedua No. 606, Bogor"),
    Friend(19, R.drawable.image19, "M. Adli Uhaq", "M.Adli.Uhaq@gmail.com", "Jalan Keduabelas No. 777, Yogyakarta"),
    Friend(12, R.drawable.image12, "M. Lukman Hakim", "M.Lukman.Hakim@gmail.com", "Jalan Kelima No. 909, Tangerang"),
    Friend(15, R.drawable.image15, "M. Wildan Alif", "M.Wildan.Alif@gmail.com", "Jalan Kedelapan No. 333, Surabaya"),
    Friend(16, R.drawable.image16, "Muhammad Zacky", "Muhammad.Zacky@gmail.com", "Jalan Kesembilan No. 444, Medan"),
    Friend(18, R.drawable.image18, "Nazrul Ihsan", "Nazrul.Ihsan@gmail.com", "Jalan Kesebelas No. 666, Malang"),
    Friend(17, R.drawable.image17, "Putri Maharani", "Putri.Maharani@gmail.com", "Jalan Kesepuluh No. 555, Semarang"),
    Friend(4, R.drawable.image4, "Raif Haqqi Fazilla. H", "Raif.Haqqi.Fazilla.H@gmail.com", "Jalan Rendah No. 101, Medan"),
    Friend(6, R.drawable.image6, "Rani Dwi Sopia", "Rani.Dwi.Sopia@gmail.com", "Jalan Sentral No. 303, Malang"),
    Friend(14, R.drawable.image14, "Rialin Ardiansah", "Rialin.Ardiansah@gmail.com", "Jalan Ketujuh No. 222, Bandung"),
    Friend(8, R.drawable.image8, "Rikerno Aldifo", "Rikerno.Aldifo@gmail.com", "Jalan Utama No. 505, Bali"),
    Friend(13, R.drawable.image13, "Zahra Anisa", "Zahra.Anisa@gmail.com", "Jalan Keenam No. 111, Jakarta"),
    Friend(20, R.drawable.image20, "Zhafira Ramadhani", "Zhafira.Ramadhani@gmail.com", "Jalan Ketigabelas No. 888, Bali")

)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FriendsApp()
            }
        }
    }
}

@Composable
fun FriendsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "friends_list") {
        composable("friends_list") {
            FriendsListScreen(navController)
        }
        composable(
            "friend_detail/{friendId}",
            arguments = listOf(navArgument("friendId") { type = NavType.IntType })
        ) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getInt("friendId") ?: 0
            val friend = friendsData.first { it.id == friendId }
            FriendDetailScreen(friend, navController)
        }
    }
}



@Composable
fun FriendItem(friend: Friend, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Padding luar untuk jarak antar item
            .padding(horizontal = 16.dp, vertical = 8.dp)
            // Pastikan seluruh item dapat diklik
            .clickable { onItemClick() }
            // Tambahkan border dengan gradient dan rounded corner
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(colors = listOf(Color.Magenta, Color.Cyan)),
                shape = RoundedCornerShape(12.dp)
            )
            // Padding dalam agar isi tidak terlalu mepet dengan border
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        ) {
            Image(
                painter = rememberImagePainter(data = friend.photo),
                contentDescription = "Foto ${friend.name}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = friend.name, style = MaterialTheme.typography.h6)
            Text(text = friend.email, style = MaterialTheme.typography.body2)
            Text(text = friend.address, style = MaterialTheme.typography.body2)
        }
    }
}


@Composable
fun FriendDetailScreen(friend: Friend, navController: NavController) {
    val archivoblack = FontFamily(Font(R.font.archivoblack, FontWeight.Normal))

    Column(modifier = Modifier.fillMaxSize()) {
        // Header dengan judul "Friend Detail" (tanpa tombol Back)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Text(
                text = "Friend Detail",
                color = Color.White,
                style = MaterialTheme.typography.h4.copy(fontFamily = archivoblack),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // Konten detail ditempatkan dengan weight agar mengisi ruang yang tersedia
        val configuration = LocalConfiguration.current
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Tampilan lanskap: gambar dan detail berdampingan
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Bagian gambar
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 3.dp,
                            brush = Brush.linearGradient(colors = listOf(Color.Magenta, Color.Cyan)),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = friend.photo),
                        contentDescription = "Foto ${friend.name}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Bagian detail
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        .border(
                            width = 3.dp,
                            brush = Brush.linearGradient(colors = listOf(Color.Blue, Color.Green)),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF4A00E0), Color(0xFF1E88E5))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = friend.name,
                        style = MaterialTheme.typography.h4.copy(
                            color = Color.White,
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = friend.email,
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.White,
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = friend.address,
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.White,
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Tampilan potret: gambar di atas, detail di bawah
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = friend.photo),
                        contentDescription = "Foto ${friend.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(375.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                width = 3.dp,
                                brush = Brush.linearGradient(colors = listOf(Color.Magenta, Color.Cyan)),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        .border(
                            width = 3.dp,
                            brush = Brush.linearGradient(colors = listOf(Color.Blue, Color.Green)),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF4A00E0), Color(0xFF1E88E5))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = friend.name,
                        style = MaterialTheme.typography.h4.copy(
                            color = Color.White,
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = friend.email,
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.White,
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = friend.address,
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.White,
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Tombol Back diletakkan di bawah layar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsListScreen(navController: NavController) {
    // Definisikan FontFamily dari resource
    val archivoblack = FontFamily(
        Font(R.font.archivoblack, FontWeight.Normal)
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        stickyHeader {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "FriendList",
                        color = Color.White,
                        style = MaterialTheme.typography.h4.copy(
                            fontFamily = archivoblack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
        items(friendsData) { friend ->
            FriendItem(friend = friend, onItemClick = {
                navController.navigate("friend_detail/${friend.id}")
            })
        }
    }
}