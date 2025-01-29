let basket = {};
window.items = [];

// Load items from a JSON file or API
async function loadItems() {
  try {
    const response = await fetch("/items.json");
    if (!response.ok) {
      throw new Error(`Failed to load items: ${response.statusText}`);
    }
    window.items = await response.json();
  } catch (error) {
    console.error("Error loading items:", error);
  }
}

function fetchBasket() {
  fetch("http://localhost:8080/api/basket", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "localhost:3000",
      "Access-Control-Allow-Methods": "GET",
      "Access-Control-Allow-Credentials": "true",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Failed to load basket: ${response.statusText}`);
      }
      return response.text();
    })
    .then((data) => {
      if (!data) return;

      basket = {};
      // Convert string into object and update basket
      const itemsArray = data.split(",");
      itemsArray.forEach((item) => {
        const [id, quantity] = item.split(":");
        basket[id] = { id: parseInt(id), quantity: parseInt(quantity) };
      });

      updateBasketDisplay();
    })
    .catch((error) => {
      console.error("Error loading basket:", error);
      alert("Failed to load basket. Please try again later.");
    });
}

function saveBasket() {
  const itemsArray = Object.values(basket);
  let msTaken = new Date().getTime();

  // Convert into string and send to the server, as such:
  // "id:quanity,id:quantity,id:quantity", e.g. "1:2,2:1,3:1"
  let itemsArrayString = "";
  itemsArray.forEach((item) => {
    itemsArrayString += `${item.id}:${item.quantity},`;
  });
  if (itemsArrayString.length > 0)
    itemsArrayString = itemsArrayString.slice(0, -1);
  console.log(itemsArrayString);

  fetch("http://localhost:8080/api/basket", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "localhost:3000",
      "Access-Control-Allow-Methods": "POST",
      "Access-Control-Allow-Credentials": "true",
    },
    body: itemsArrayString,
  })
    .then((response) => {
      // Calculate time taken to save basket
      msTaken = new Date().getTime() - msTaken;
      document.getElementById("response-time").textContent = msTaken;

      if (!response.ok) {
        throw new Error(`Failed to save basket: ${response.statusText}`);
      }

      return response.text();
    })
    .catch((error) => {
      console.error("Error saving basket:", error);
      alert("Failed to save basket. Please try again later.");
    });
}

// Add item to basket
function addToBasket(itemId) {
  if (!window.items || window.items.length === 0) {
    alert("Items are not loaded yet. Please try again later.");
    return;
  }

  const item = window.items.find((i) => i.id === itemId);
  if (!item) return;

  if (basket[itemId]) {
    basket[itemId].quantity += 1;
  } else {
    basket[itemId] = { id: item.id, quantity: 1 };
  }

  saveBasket();
  updateBasketDisplay();
}

// Remove item from basket
function removeFromBasket(itemId) {
  if (basket[itemId]) {
    basket[itemId].quantity -= 1;
    if (basket[itemId].quantity <= 0) {
      delete basket[itemId];
    }

    saveBasket();
    updateBasketDisplay();
  }
}

// Clear basket
function clearBasket() {
  basket = {};

  saveBasket();
  updateBasketDisplay();
}

// Purchase items
function checkoutItems() {
  alert("This operation is not supported in the demo.");
}

// Update basket display
function updateBasketDisplay() {
  const basketItems = document.getElementById("basket-items");
  const basketTotal = document.getElementById("basket-total");
  basketItems.innerHTML = "";
  let total = 0;

  if (Object.keys(basket).length === 0) {
    basketItems.innerHTML = `
      <p class="text-gray-500 text-center">Your basket is empty.</p>
    `;
    basketTotal.textContent = "$0.00";
    return;
  }

  Object.values(basket).forEach((item) => {
    const itemData = window.items.find((i) => i.id === item.id);
    total += itemData.price * item.quantity;

    const itemElement = document.createElement("div");
    itemElement.className = "flex justify-between items-center mb-2";
    itemElement.innerHTML = `
      <div class="flex-1">
        <h4 class="font-bold">${itemData.name}</h4>
        <p class="text-sm text-gray-600">$${itemData.price.toFixed(2)} x ${item.quantity}</p>
      </div>
      <button onclick="removeFromBasket(${item.id})" class="ml-4 text-red-500 hover:text-red-700">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
        </svg>
      </button>
    `;

    basketItems.appendChild(itemElement);
  });

  basketTotal.textContent = `$${total.toFixed(2)}`;
}

// Load items on page load
document.addEventListener("DOMContentLoaded", async () => {
  await loadItems(); // Load items before user interactions
  fetchBasket(); // Load the basket from the server
  updateBasketDisplay(); // Initialize the basket display
});

// Expose functions globally for onclick handlers
window.addToBasket = addToBasket;
window.removeFromBasket = removeFromBasket;
window.clearBasket = clearBasket;
window.checkoutItems = checkoutItems;
