document.addEventListener('DOMContentLoaded', function () {
    fetch('navbar.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('header').innerHTML = data;

            // 로그인, 로그아웃 버튼
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

            document.querySelectorAll('#sidebar .components li a').forEach(link => {
                link.addEventListener('click', function (event) {
                    event.preventDefault(); // 기본 동작 중단
                    const targetUrl = this.href;

                    // 홈 페이지는 권한 확인 없이 이동
                    if (targetUrl.endsWith('/')) {
                        window.location.href = targetUrl;
                        return;
                    }

// API 호출로 권한 확인
                    sendRequestWithToken(targetUrl, 'GET')
                        .then(response => {
                            if (response.status === 200) {
                                window.location.href = targetUrl; // 권한이 있는 경우 페이지 이동
                            } else if (response.status === 401) {
                                alert('권한이 필요합니다.');
                                window.location.href = '/login.html'; // 권한이 없는 경우 로그인 페이지로 이동
                            } else {
                                // 200, 401 외의 다른 상태 코드 처리
                                console.error(`Unexpected response status: ${response.status}`);
                                alert('예상치 못한 오류가 발생했습니다.');
                            }
                        })
                        .catch(error => {
                            if (error.message === 'No access token available') {
                                alert('로그인이 필요합니다.');
                                window.location.href = '/login.html';
                            } else {
                                console.error('Error:', error);
                                alert('요청 중 오류가 발생했습니다.');
                            }
                        });
                });
            });
        })
        .catch(error => console.log('Error loading navbar:', error));
});
