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

            // 사용자 역할 확인 및 프로필 요청 항목 표시
            checkUserRole();

            document.querySelectorAll('#sidebar .components li a').forEach(link => {
                link.addEventListener('click', function (event) {
                    event.preventDefault(); // 기본 동작 중단
                    const targetUrl = this.href;

                    // 홈 페이지는 권한 확인 없이 이동
                    if (targetUrl.endsWith('/') || targetUrl.endsWith('#')) {
                        window.location.href = targetUrl;
                        return;
                    }

                    // API 호출로 권한 확인
                    sendRequestWithToken(targetUrl, 'GET', null, null)
                        .then(response => {
                            if (response.status === 200) {
                                window.location.href = targetUrl; // 권한이 있는 경우 페이지 이동
                            }
                        })
                        .catch(error => {
                            if (error.message === 'No access token available') {
                                console.log('navbar');
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

// 역할 확인 함수
function getUserRole() {
    const token = getAccessToken(); // accessToken을 가져옵니다.
    if (!token) return null; // 토큰이 없으면 null 반환

    const payload = token.split('.')[1]; // JWT의 페이로드 부분을 가져옵니다.
    const decoded = JSON.parse(atob(payload)); // Base64로 인코딩된 페이로드를 디코드합니다.

    return decoded.role || null; // role 속성을 반환합니다. 없으면 null 반환
}

// 사용자 역할 확인 및 프로필 요청 항목 표시
function checkUserRole() {
    const userRole = getUserRole();
    const profileRequestItem = document.getElementById('profileRequestItem');
    if (userRole === 'ROLE_ADMIN') {
        profileRequestItem.style.display = 'block'; // admin인 경우 표시
    } else {
        profileRequestItem.style.display = 'none'; // admin이 아닐 경우 숨김
    }
}
