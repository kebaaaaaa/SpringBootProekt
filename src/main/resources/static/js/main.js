// AutoPark Main JavaScript File

document.addEventListener('DOMContentLoaded', function() {
    // Mobile menu toggle (if needed in future)
    const header = document.querySelector('header');
    
    // Add scroll effect to header
    window.addEventListener('scroll', function() {
        if (window.scrollY > 50) {
            header.style.backgroundColor = '#1a252f';
            header.style.boxShadow = '0 2px 10px rgba(0,0,0,0.1)';
        } else {
            header.style.backgroundColor = '#2c3e50';
            header.style.boxShadow = 'none';
        }
    });
    
    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            if (targetId !== '#') {
                const targetElement = document.querySelector(targetId);
                if (targetElement) {
                    targetElement.scrollIntoView({
                        behavior: 'smooth'
                    });
                }
            }
        });
    });
    
    // Form validation (if forms exist)
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            // Basic validation - prevent empty required fields
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    field.style.borderColor = '#e74c3c';
                } else {
                    field.style.borderColor = '';
                }
            });
            
            if (!isValid) {
                e.preventDefault();
                alert('Please fill in all required fields');
            }
        });
    });
    
    // Active nav link based on current page
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';
    const navLinks = document.querySelectorAll('nav a');
    
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPage) {
            link.classList.add('active');
        }
    });

    // Show logged user and session-aware nav actions
    setupAuthUi();
});

async function setupAuthUi() {
    const navList = document.querySelector("nav ul");
    if (!navList) {
        return;
    }

    const loginLink = Array.from(navList.querySelectorAll("a"))
        .find(link => link.getAttribute("href") === "login.html");

    try {
        const response = await fetch("/api/auth/me", { credentials: "same-origin" });
        if (!response.ok) {
            return;
        }

        const me = await response.json();

        if (loginLink) {
            loginLink.textContent = `Signed in: ${me.username}`;
            loginLink.setAttribute("href", "#");
        }

        const logoutLi = document.createElement("li");
        const logoutForm = document.createElement("form");
        logoutForm.setAttribute("method", "post");
        logoutForm.setAttribute("action", "/perform_logout");
        logoutForm.style.display = "inline";

        const logoutButton = document.createElement("button");
        logoutButton.type = "submit";
        logoutButton.textContent = "Logout";
        logoutButton.className = "btn-primary";
        logoutButton.style.padding = "6px 12px";
        logoutButton.style.fontSize = "14px";
        logoutButton.style.cursor = "pointer";

        logoutForm.appendChild(logoutButton);
        logoutLi.appendChild(logoutForm);
        navList.appendChild(logoutLi);
    } catch (error) {
        // Keep guest navigation when auth check fails.
    }
}