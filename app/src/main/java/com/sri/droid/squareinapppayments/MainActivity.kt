package com.sri.droid.squareinapppayments

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sri.droid.squareinapppayments.ui.theme.SquareInAppPaymentsTheme
import sqip.Callback
import sqip.CardDetails
import sqip.CardEntry
import sqip.CardEntry.DEFAULT_CARD_ENTRY_REQUEST_CODE
import sqip.CardEntry.handleActivityResult
import sqip.CardEntryActivityResult


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SquareInAppPaymentsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }

            SquarePaymentButton {
                startCardEntryActivity()
            }
        }
    }

    private fun startCardEntryActivity() {
        CardEntry.startCardEntryActivity( this, true,
            DEFAULT_CARD_ENTRY_REQUEST_CODE);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleActivityResult(data,
            object : Callback<CardEntryActivityResult> {
                override fun onResult(result: CardEntryActivityResult) {
                    if (result.isSuccess()) {
                        val cardResult: CardDetails = result.getSuccessValue()
                        val (brand, lastFourDigits, expirationMonth, expirationYear, postalCode, type, prepaidType) = cardResult.card
                        val nonce = cardResult.nonce
                    } else if (result.isCanceled()) {
                        Toast.makeText(
                            this@MainActivity,
                            "Canceled",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun SquarePaymentButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Pay with Card")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SquareInAppPaymentsTheme {
        Greeting("Android")
    }
}