document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");
    const createForm = document.getElementById("create-form");
    const petsContainer = document.getElementById("pets-container");
    const authContainer = document.getElementById("auth");
    const petsSection = document.getElementById("pets");
    const logoutButton = document.getElementById("logout");

    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const username = document.getElementById("login-username").value;
        const password = document.getElementById("login-password").value;

        const response = await fetch("/user/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userName: username, userPassword: password })
        });

        if (response.ok) {
            const user = await response.json();
            sessionStorage.setItem("token", user.userToken);
            loadPets(user.id);
        } else {
            alert("Login failed!");
        }
    });

    createForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const username = document.getElementById("create-username").value;
        const password = document.getElementById("create-password").value;
        const role = document.getElementById("create-role").value;

        const response = await fetch("/user/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userName: username, userPassword: password, userRole: role })
        });

        if (response.ok) {
            alert("User created successfully!");
        } else {
            alert("User creation failed!");
        }
    });

    async function loadPets(userId) {
        const response = await fetch(`/user/get/${userId}`, {
            headers: { "Authorization": `Bearer ${sessionStorage.getItem("userToken")}` }
        });

        if (response.ok) {
            const user = await response.json();
            displayPets(user.petList);
            authContainer.style.display = "none";
            petsSection.style.display = "block";
        } else {
            alert("Failed to load pets!");
        }
    }

    function displayPets(pets) {
        petsContainer.innerHTML = "";
        const positions = ["left: 0;", "left: 150px;", "left: 300px;"];
        pets.forEach((pet, index) => {
            const petElement = document.createElement("img");
            petElement.src = `images/${pet.breed}_${pet.color}.gif`;
            petElement.classList.add("pet");
            petElement.style = positions[index];
            petsContainer.appendChild(petElement);
        });
    }

    logoutButton.addEventListener("click", () => {
        sessionStorage.removeItem("token");
        authContainer.style.display = "block";
        petsSection.style.display = "none";
    });

    if (sessionStorage.getItem("token")) {
        // Load pets if already logged in
        // This is a simplified example, you may need to adjust to get the userId
        const userId = "replace_with_logged_in_user_id"; 
        loadPets(userId);
    }
});
