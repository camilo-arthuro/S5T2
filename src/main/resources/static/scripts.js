const API_URL = 'http://localhost:8080';

async function registerUser() {
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;
    const role = document.getElementById('register-role').value;

    const response = await fetch(`${API_URL}/user/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: username, password, role })
    });

    if (response.ok) {
        alert('User registered successfully');
    } else {
        alert('Failed to register user');
    }
}

async function loginUser() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    const response = await fetch(`${API_URL}/user/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        alert('Login successful');
        document.getElementById('pet-section').style.display = 'block';
    } else {
        alert('Failed to login');
    }
}

async function getUserPets() {
    const userId = document.getElementById('get-user-id').value;

    const response = await fetch(`${API_URL}/user/get/${userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    if (response.ok) {
        const data = await response.json();
        console.log(data);
    } else {
        alert('Failed to get user pets');
    }
}

async function deleteUser() {
    const userId = document.getElementById('delete-user-id').value;

    const response = await fetch(`${API_URL}/user/delete/${userId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    if (response.ok) {
        alert('User deleted successfully');
    } else {
        alert('Failed to delete user');
    }
}

async function createPet() {
    const userId = document.getElementById('create-pet-user-id').value;
    const petName = document.getElementById('create-pet-name').value;
    const petColor = document.getElementById('create-pet-color').value;
    const petBreed = document.getElementById('create-pet-breed').value;

    const response = await fetch(`${API_URL}/pet/create/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({ petName, petColor, petBreed })
    });

    if (response.ok) {
        alert('Pet created successfully');
    } else {
        alert('Failed to create pet');
    }
}

async function updatePet() {
    const userId = document.getElementById('update-pet-user-id').value;
    const petId = document.getElementById('update-pet-id').value;
    const petName = document.getElementById('update-pet-name').value;
    const update = document.getElementById('update-pet-update').value;
    const change = document.getElementById('update-pet-change').value;

    const response = await fetch(`${API_URL}/pet/update/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({ petId, petName, update, change })
    });

    if (response.ok) {
        alert('Pet updated successfully');
    } else {
        alert('Failed to update pet');
    }
}

async function deletePet() {
    const userId = document.getElementById('delete-pet-user-id').value;
    const petId = document.getElementById('delete-pet-id').value;
    const petName = document.getElementById('delete-pet-name').value;

    const response = await fetch(`${API_URL}/pet/delete/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({ petId, petName })
    });

    if (response.ok) {
        alert('Pet deleted successfully');
    } else {
        alert('Failed to delete pet');
    }
}

async function petAction() {
    const userId = document.getElementById('action-pet-user-id').value;
    const petId = document.getElementById('action-pet-id').value;
    const petName = document.getElementById('action-pet-name').value;
    const action = document.getElementById('action-pet-action').value;

    const response = await fetch(`${API_URL}/pet/action/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({ petId, petName, action })
    });

    if (response.ok) {
        alert('Pet action successful');
    } else {
        alert('Failed to perform pet action');
    }
}
