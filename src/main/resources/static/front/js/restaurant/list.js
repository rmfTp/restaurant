window.addEventListener('DOMContentLoaded', function() {
    const mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(35.097993954721815, 129.03321328510245), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨 낮을 수록 자세히
        };

    const map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 지도에 표시된 마커 객체를 가지고 있을 배열입니다
    const markers = [];

    // 마커 하나를 지도위에 표시합니다
    addMarker(new kakao.maps.LatLng(33.450701, 126.570667));
});

// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
function setMarkers(map) {
    for (const i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}