package jhon.solis.dev.searchshoppapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import jhon.solis.dev.domain.model.Product
import jhon.solis.dev.domain.util.Result
import jhon.solis.dev.searchshoppapp.utils.SortOption

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val productsState by homeViewModel.products.collectAsState()

    Scaffold(
        topBar = {
            ShopAppTopAppBar(
                onSortOptionSelected = {
                    homeViewModel.updateQuery(
                        queryProduct = homeViewModel.queryProduct.value.copy(order = it.ordinal)
                    )
                    homeViewModel.searchProducts()
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 8.dp)) {
                SearchProductBarHistory(
                    onSearch = { query ->
                        homeViewModel.updateQuery(
                            queryProduct = homeViewModel.queryProduct.value.copy(query = query)
                        )
                        homeViewModel.searchProducts()
                    }
                )

                when (productsState) {
                    is Result.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFFB90000),
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    is Result.Error -> {
                        val error = productsState as Result.Error
                        Text(
                            text = "Error: ${error.message}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    is Result.Success -> {
                        val products = (productsState as Result.Success<List<Product>>).data
                        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                            items(products) { product ->
                                ProductItem(product) {

                                }
                            }

                            if(products.isNotEmpty()){
                                item {

                                }
                            }

                        }
                    }

                    null -> {
                        Text(
                            text = "Busca un producto para comenzar :)",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        if (product.imageUrl.isNotBlank()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.productName,
                modifier = Modifier.size(80.dp)
            )
        }
        else {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("Sin imagen", color = Color.White)
            }
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(product.productName, style = MaterialTheme.typography.titleSmall)

            Spacer(modifier = Modifier.height(8.dp))

            Text("$${product.priceBeforeDiscount}", style = TextStyle(textDecoration = TextDecoration.LineThrough, color = Color.Gray))

            Text("$${product.priceAfterDiscount}", style = MaterialTheme.typography.bodyMedium, color = Color.Red)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                product.colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(shape = CircleShape)
                            .background(color = Color(color.toColorInt()))
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopAppTopAppBar(
    onSortOptionSelected: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Shopp App Products",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Ordenar"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Menor precio a mayor") },
                        onClick = {
                            expanded = false
                            onSortOptionSelected(SortOption.ASCENDING)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Mayor precio a menor") },
                        onClick = {
                            expanded = false
                            onSortOptionSelected(SortOption.DESCENDING)
                        }
                    )
                }
            }
        },
        modifier = modifier
    )
}


@Composable
private fun SearchProductBarHistory(
    onSearch: (String) -> Unit,
) {
    var localQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = localQuery,
                    onValueChange = {
                        localQuery = it
                    },
                    label = { Text("Buscar producto") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFB90000),
                        cursorColor = Color(0xFFB90000),
                        focusedLabelColor = Color(0xFFB90000),
                        focusedTextColor = Color(0xFFB90000),
                        unfocusedTextColor = Color(0xFFB90000)
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(
                    onClick = {
                        if (localQuery.isNotBlank()) {
                            onSearch(localQuery)
                        }
                    },
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF88002A))
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme{
        ProductItem(
            product = Product(
                id = "",
                productName = "Laptop 2 en 1 HP Pavilion X360 14-EK1011LA 14 pulgadas Full HD Intel Core i5 Intel Iris XE 8 GB RAM 512 GB SSD",
                priceBeforeDiscount = 20629.0,
                priceAfterDiscount = 13895.1,
                imageUrl = "https://ss628.liverpool.com.mx/sm/1171318234.jpg",
                colors = listOf("#19157e"),
            ),
            onClick = {}
        )
    }
}

