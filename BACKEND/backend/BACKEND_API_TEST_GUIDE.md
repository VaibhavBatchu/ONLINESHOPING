# 🧪 Backend API Test Guide

## Test Cart Add Endpoint

### ✅ Correct Format (Query Parameters)

The backend expects **query parameters**, NOT a JSON body.

#### Using cURL:
```bash
# Replace with valid IDs from your MongoDB
curl -X POST "http://localhost:2004/cart/add?buyerId=YOUR_BUYER_ID&productId=YOUR_PRODUCT_ID&quantity=2"
```

#### Using Postman:
1. **Method:** POST
2. **URL:** `http://localhost:2004/cart/add`
3. **Params Tab:**
   - `buyerId` = `507f1f77bcf86cd799439011` (example)
   - `productId` = `507f191e810c19729de860ea` (example)
   - `quantity` = `1`
4. **Headers:** No Content-Type needed
5. Click **Send**

#### Using JavaScript (Axios):
```javascript
// ✅ CORRECT WAY
axios.post('http://localhost:2004/cart/add', null, {
  params: {
    buyerId: '507f1f77bcf86cd799439011',
    productId: '507f191e810c19729de860ea',
    quantity: 1
  }
})
.then(response => console.log('Success:', response.data))
.catch(error => console.error('Error:', error.response?.data));
```

```javascript
// ✅ ALSO CORRECT (using query string)
axios.post(`http://localhost:2004/cart/add?buyerId=${buyerId}&productId=${productId}&quantity=${quantity}`)
.then(response => console.log('Success:', response.data))
.catch(error => console.error('Error:', error.response?.data));
```

#### Using Fetch API:
```javascript
// ✅ CORRECT
const buyerId = '507f1f77bcf86cd799439011';
const productId = '507f191e810c19729de860ea';
const quantity = 1;

fetch(`http://localhost:2004/cart/add?buyerId=${buyerId}&productId=${productId}&quantity=${quantity}`, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

---

## ❌ Wrong Format (Will Give 400 Error)

```javascript
// ❌ WRONG - JSON Body
axios.post('http://localhost:2004/cart/add', {
  buyerId: '507f1f77bcf86cd799439011',
  productId: '507f191e810c19729de860ea',
  quantity: 1
})
```

```javascript
// ❌ WRONG - Nested Objects
axios.post('http://localhost:2004/cart/add', {
  buyer: { id: '507f1f77bcf86cd799439011' },
  product: { id: '507f191e810c19729de860ea' },
  quantity: 1
})
```

---

## 📝 Expected Response

### Success (200 OK):
```json
{
  "id": "65abc123def456789",
  "quantity": 1,
  "product": {
    "id": "507f191e810c19729de860ea",
    "name": "Product Name",
    "category": "Electronics",
    "description": "Product description",
    "cost": 99.99,
    "imageUrl": "https://res.cloudinary.com/.../image.jpg",
    "seller_id": "507f1f77bcf86cd799439012"
  }
}
```

### Error Responses:

#### Product Already in Cart (400):
```json
{
  "error": "Product already in cart"
}
```

#### Product Not Found (400):
```json
{
  "error": "Product does not exist"
}
```

#### Buyer Not Found (400):
```json
{
  "error": "Buyer does not exist"
}
```

#### Cart Limit Exceeded (400):
```json
{
  "error": "Cart limit exceeded"
}
```

---

## 🔍 Common Frontend Issues

### Issue 1: Sending JSON Body Instead of Query Params

**Problem in ProductDetail.jsx (Line 72):**
```javascript
// ❌ WRONG
const response = await axios.post('http://localhost:2004/cart/add', {
  buyerId: buyerId,
  productId: productId,
  quantity: 1
});
```

**Fix:**
```javascript
// ✅ CORRECT
const response = await axios.post('http://localhost:2004/cart/add', null, {
  params: {
    buyerId: buyerId,
    productId: productId,
    quantity: 1
  }
});
```

Or:

```javascript
// ✅ ALSO CORRECT
const response = await axios.post(
  `http://localhost:2004/cart/add?buyerId=${buyerId}&productId=${productId}&quantity=1`
);
```

---

### Issue 2: Using Wrong buyerId/productId Format

**Check if IDs are strings:**
```javascript
// ✅ CORRECT - MongoDB ObjectId as string
const buyerId = localStorage.getItem('buyerId'); // Should be like "507f1f77bcf86cd799439011"
const productId = product.id; // Should be string, not number

// Make sure IDs are not undefined or null
if (!buyerId) {
  console.error('Buyer ID not found in localStorage');
  return;
}

if (!productId) {
  console.error('Product ID is missing');
  return;
}
```

---

## 📋 Frontend Code Template

### React Component (ProductDetail.jsx):
```javascript
import axios from 'axios';

const handleAddToCart = async () => {
  try {
    // Get buyer ID from localStorage or context
    const buyerId = localStorage.getItem('buyerId');
    
    if (!buyerId) {
      alert('Please login first!');
      return;
    }
    
    if (!product?.id) {
      alert('Product information is missing!');
      return;
    }
    
    // Call API with query parameters
    const response = await axios.post(
      `http://localhost:2004/cart/add?buyerId=${buyerId}&productId=${product.id}&quantity=1`
    );
    
    console.log('Added to cart:', response.data);
    alert('Product added to cart successfully!');
    
    // Update cart count in UI
    // fetchCartCount();
    
  } catch (error) {
    console.error('Error adding to cart:', error);
    
    if (error.response) {
      // Server responded with error
      const errorMsg = error.response.data?.error || error.response.data || 'Failed to add to cart';
      alert(errorMsg);
    } else {
      // Network error
      alert('Network error. Please check if backend is running.');
    }
  }
};
```

---

## 🧪 Test Steps

### 1. Get Valid IDs from MongoDB

```bash
# Connect to MongoDB
mongosh "mongodb+srv://root:root@laxman.xmhzqpt.mongodb.net/llcart"

# Get a buyer ID
db.buyers.findOne({}, {_id: 1})

# Get a product ID
db.products.findOne({}, {_id: 1})
```

### 2. Test with cURL

```bash
# Replace with actual IDs
curl -X POST "http://localhost:2004/cart/add?buyerId=ACTUAL_BUYER_ID&productId=ACTUAL_PRODUCT_ID&quantity=1"
```

### 3. Check Console Logs

**Backend (Spring Boot):**
- Should see no errors
- Check logs for any exceptions

**Frontend (Browser Console - F12):**
- Check Network tab for request details
- Look at request URL (should have query parameters)
- Look at response status and body

---

## 🎯 Quick Checklist

Before testing, verify:
- ✅ Spring Boot application is running (port 2004)
- ✅ MongoDB is connected
- ✅ You have valid buyer and product IDs
- ✅ Frontend is using query parameters, NOT JSON body
- ✅ IDs are strings (MongoDB ObjectId format)
- ✅ CORS is enabled (already set in backend)

---

## 🆘 Still Getting 400 Error?

**Send me your frontend code:**
1. `ProductDetail.jsx` (specifically the add to cart function)
2. Any API service files
3. Screenshot of browser Network tab showing the request

I'll help you fix it immediately! 🚀
