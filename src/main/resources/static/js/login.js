document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const loginMessage = document.getElementById("loginMessage");
    if (!loginForm) {
        return;
    }

    const params = new URLSearchParams(window.location.search);
    if (params.get("error") === "true") {
        loginMessage.textContent = "Invalid username or password.";
        loginMessage.style.color = "#e74c3c";
    } else if (params.get("logout") === "true") {
        loginMessage.textContent = "You have been logged out.";
        loginMessage.style.color = "#2ecc71";
    }

    fetch("/api/auth/me", { credentials: "same-origin" })
        .then(response => {
            if (response.ok) {
                window.location.replace("/index.html");
            }
        })
        .catch(() => {
            // Keep login form visible for anonymous users.
        });

    loginForm.addEventListener("submit", function () {
        // Browser submits the form directly to Spring Security.
        // This ensures session cookie (JSESSIONID) is created reliably.
    });
});
