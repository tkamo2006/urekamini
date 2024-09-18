// navbar.js 파일
document.addEventListener('DOMContentLoaded', function () {
    fetch('navbar.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('header').innerHTML = data;

            //로그인, 로그아웃 버튼
            const token = localStorage.getItem('accessToken');
            const logoutBtn = document.getElementById('logoutBtn');
            const loginBtn = document.getElementById('loginBtn');

            if (token) {
                logoutBtn.style.display = 'block';
                loginBtn.style.display = 'none';
                logoutBtn.addEventListener('click', function () {
                    // auth.js에 있는 logout 함수 호출
                    logout();
                });
            } else {
                loginBtn.style.display = 'block';
                logoutBtn.style.display = 'none';
                loginBtn.addEventListener('click', function () {
                    window.location.href = '/login.html';
                });
            }
        })
        .catch(error => console.log('Error loading navbar:', error));
});
