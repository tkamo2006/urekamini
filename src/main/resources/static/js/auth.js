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

export async function logout() {
    try {
        console.log("로그아웃");
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
