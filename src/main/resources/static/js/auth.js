// auth.js
export function getAuthHeaders() {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
        throw new Error("No access token found");
    }

    return {
        "Authorization": "Bearer " + accessToken
    };
}

export function isLoggedIn() {
    return !!localStorage.getItem('accessToken');
}

export async function logout() {
    try {
        let response = await fetch('/logout', {
            method: 'POST',
            credentials: 'include', // 요청에 쿠키 포함
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            localStorage.removeItem("accessToken");
            alert("로그아웃 되었습니다.");
            window.location.href = "/login.html"; // 로그인 페이지로 리디렉션
        } else {
            alert("로그아웃에 실패했습니다.");
        }
    } catch (error) {
        console.error('로그아웃 중 오류 발생:', error);
        alert("로그아웃 과정에서 오류가 발생했습니다.");
    }
}


// 전역 범위에서 호출 가능하게 하기 위해 추가
window.logout = logout;


// 토큰 처리 로직을 분리
function getAccessToken() {
    return localStorage.getItem('accessToken');
}
window.getAccessToken = getAccessToken;

function setAccessToken(token) {
    localStorage.setItem('accessToken', token);
}

function sendRequestWithToken(url, method) {
    const token = getAccessToken();
    if (!token) {
        // 토큰이 없는 경우에는 에러를 반환하거나, 적절한 처리를 할 수 있습니다.
        return Promise.reject(new Error('No access token available'));
    }
    return fetch(url, {
        method: method,
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });
}
window.sendRequestWithToken = sendRequestWithToken;
