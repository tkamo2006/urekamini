document.addEventListener('DOMContentLoaded', function() {
    fetch('navbar.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('header').innerHTML = data;
        })
        .catch(error => console.log('Error loading navbar:', error));
});