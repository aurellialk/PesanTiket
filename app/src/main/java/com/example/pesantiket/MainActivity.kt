package com.example.pesantiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var hargaTiket by remember {
                mutableIntStateOf(50000)
            }

            var jumlahTiket by remember {
                mutableIntStateOf(1)
            }

            var namaPembeli by remember {
                mutableStateOf("")
            }

            var statusPesanan by remember {
                mutableStateOf("Nama Masih Kosong")
            }

            var orderAttempt by remember {
                mutableIntStateOf(0)
            }

            LaunchedEffect(orderAttempt) {
                if (orderAttempt > 0) {
                    if (namaPembeli.isBlank()) {
                        statusPesanan = "Nama Masih Kosong"
                    } else {
                        statusPesanan = "Memproses pesanan..."

                        delay(5000)

                        statusPesanan = "Tiket telah dipesan"
                    }
                }
            }

            TicketScreen(
                hargaTiket = hargaTiket,
                jumlahTiket = jumlahTiket,
                namaPembeli = namaPembeli,
                statusPesanan = statusPesanan,
                totalBayar = hargaTiket * jumlahTiket,
                onJumlahTiketChange = {
                    jumlahTiket = it
                },
                onNamaPembeliChange = {
                    namaPembeli = it
                },
                onPesanClick = {
                    orderAttempt++
                }
            )
        }
    }
}

@Composable
fun TicketScreen(
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    statusPesanan: String,
    totalBayar: Int,
    onJumlahTiketChange: (Int) -> Unit,
    onNamaPembeliChange: (String) -> Unit,
    onPesanClick: () -> Unit
) {
    val rupiah = { value: Int ->
        val symbols = DecimalFormatSymbols(Locale("id", "ID"))
        DecimalFormat("#,###", symbols).format(value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F7FC))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF1769D2),
                    shape = RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
                .padding(
                    start = 24.dp,
                    top = 42.dp,
                    end = 24.dp,
                    bottom = 30.dp
                )
        ) {
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF247BE0)
                )
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 22.dp,
                        vertical = 20.dp
                    )
                ) {
                    Text(
                        text = "HARGA TIKET",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Rp${rupiah(hargaTiket)}",
                        color = Color.White,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Nama Pembeli",
                        color = Color(0xFF18345E),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    OutlinedTextField(
                        value = namaPembeli,
                        onValueChange = onNamaPembeliChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Masukkan nama lengkap",
                                color = Color(0xFF9AA8BA)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(13.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Jumlah Tiket",
                        color = Color(0xFF18345E),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFF2F7FD),
                                RoundedCornerShape(18.dp)
                            )
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                if (jumlahTiket > 1) {
                                    onJumlahTiketChange(jumlahTiket - 1)
                                }
                            },
                            modifier = Modifier.width(54.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFDCEBFC),
                                contentColor = Color(0xFF1769D2)
                            )
                        ) {
                            Text(
                                text = "−",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "$jumlahTiket",
                            color = Color(0xFF18345E),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = {
                                onJumlahTiketChange(jumlahTiket + 1)
                            },
                            modifier = Modifier.width(54.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFDCEBFC),
                                contentColor = Color(0xFF1769D2)
                            )
                        ) {
                            Text(
                                text = "+",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF155FC1)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOTAL PEMBAYARAN",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Rp${rupiah(totalBayar)}",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = onPesanClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1769D2)
                )
            ) {
                Text(
                    text = "Pesan Tiket",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(17.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (statusPesanan) {
                        "Tiket telah dipesan" -> Color(0xFFE5F6EB)
                        "Memproses pesanan..." -> Color(0xFFE5F1FD)
                        else -> Color(0xFFEAF3FE)
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 15.dp
                    )
                ) {
                    Text(
                        text = "STATUS PESANAN",
                        color = Color(0xFF4085DC),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = statusPesanan,
                        color = when (statusPesanan) {
                            "Tiket telah dipesan" -> Color(0xFF218739)
                            else -> Color(0xFF1769D2)
                        },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}