// Scroll Effect
window.addEventListener('scroll', () => {
  const header = document.querySelector('header');
  if (window.scrollY > 70) {
    header.classList.add('scrolled');
  } else {
    header.classList.remove('scrolled');
  }
});

// Mobile Menu Toggle
document.addEventListener('DOMContentLoaded', () => {
  const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
  const navMenu = document.querySelector('.nav-menu');

  if (mobileMenuToggle && navMenu) {  // Check if elements exist
    mobileMenuToggle.addEventListener('click', () => {
      navMenu.classList.toggle('show');
      mobileMenuToggle.classList.toggle('active');
    });
  }
});

document.addEventListener('DOMContentLoaded', () => {
    const dropdowns = document.querySelectorAll('nav li');

    dropdowns.forEach(dropdown => {
        dropdown.addEventListener('mouseenter', () => {
            const dropdownMenu = dropdown.querySelector('.dropdown');
            if (dropdownMenu) {
                dropdownMenu.classList.add('active');
            }
        });

        dropdown.addEventListener('mouseleave', () => {
            const dropdownMenu = dropdown.querySelector('.dropdown');
            if (dropdownMenu) {
                dropdownMenu.classList.remove('active');
            }

        });
    });
});


//document.addEventListener('DOMContentLoaded', function() {
//    fetch('/check-session')
//        .then(response => response.json())
//        .then(data => {
//            const authButtons = document.querySelector('.auth-buttons');
//            const navMenu = document.querySelector('.nav-menu');
//
//            if (data.loggedIn) {
//                authButtons.innerHTML = `
//                    <span>안녕하세요, ${data.username}님</span>
//                    <a href="/logout" id="logout">로그아웃</a>
//                `;
//            } else {
//                authButtons.innerHTML = `
//                    <a href="/user/login">로그인</a>
//                    <a href="/user/signup">회원가입</a>
//                `;
//            }
//        });
//});
