window.addEventListener("DOMContentLoaded", function() {
    if (!window.commonLib || !window.commonLib.mapLib) {
        console.error("mapLib가 로드되지 않았습니다.");
        return;
    }

    navigator.geolocation.getCurrentPosition((pos) => {
        const { latitude: lat, longitude: lon } = pos.coords;
        fetch(`/restaurant/search?lat=${lat}&lon=${lon}&cnt=50`)
            .then(res => res.json())
            .then(items => {
                mapLib.load(el, items, null, '100%', '500px');
            });
    });
    const { mapLib } = commonLib;

    const el = document.getElementById("map");

    navigator.geolocation.getCurrentPosition((pos) => {
        const { latitude: lat, longitude : lon } = pos.coords;
        fetch(`/restaurant/search?lat=${lat}&lon=${lon}&cnt=50`)
                .then(res => res.json())
                .then(items => {
                    mapLib.load(el, items, null, '100%', '500px');
                });
    });
});
window.filterRestaurants = function () {
    const checkboxes = document.querySelectorAll('input[name="category"]:checked');
    const selectedCategories = Array.from(checkboxes).map((cb) => cb.value);

    if (selectedCategories.length === 0) {
        alert("카테고리를 선택하세요.");
        return;
    }

    fetch(`/restaurant/searchByCategories?categories=${selectedCategories.join(",")}`)
        .then((res) => res.json())
        .then((items) => {
            mapLib.load(el, items, null, '100%', '500px'); // 지도 마커 업데이트

            // ✅ 목록 업데이트
            const listElement = document.getElementById("list");
            listElement.innerHTML = "";
            items.forEach((item) => {
                const li = document.createElement("li");
                li.textContent = `${item.name} - ${item.address}`;
                listElement.appendChild(li);

                // ✅ 목록 클릭 시 지도 이동
                li.addEventListener("click", () => {
                    const markerPosition = new kakao.maps.LatLng(item.lat, item.lon);
                    mapLib.map.setCenter(markerPosition);
                });
            });
        });
};