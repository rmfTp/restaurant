var commonLib = commonLib ?? {}
commonLib.mapLib = {
    init(){
        const headEl = document.head;
        if (!document.getElementById("kakao-map-sdk")){
            const scriptEl = document.createElement("script");
            scriptEl.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=1405f39183867c3cf0b2f7bb7a2c2f3b";
            scriptEl.id = "kakao-map-sdk";

            headEl.prepend(scriptEl);

            scriptEl.onLoad = () => {
                if(typeof callback === 'function'){
                    this.callback();
                }
            };
        }
    },
    /**
    *el - 지도 출력 document객체
    *items - 지도 데이터
    *center - 처음 출력시 가운데 좌표(기본값은 ip or gps 위치)
    */
    load(el, items, center, width, height) {
        if (width) {
            el.style.width = width;
        }
        if (height) {
            el.style.height = height;
        }


        if (center){
            this.showMap(el, items, center);
        }else{// 좌표 없으면 현제 위치 기반
            navigator.geolocation.getCurrentPosition((pos) => {
                const { latitude: lat, longitude : lon } = pos.coords;
                this.showMap(el, items, {lat, lon});
            });
        }
    },
    showMap(el, items, center) {
            const mapOptions = {
                center: new kakao.maps.LatLng(center.lat, center.lon),
                level: 2,
            };

            const map = new kakao.maps.Map(el, mapOptions);

            // 마커 표기
            if (!items || items.length === 0) return;
            items.forEach(({lat, lon, name, roadAddress}) => {
                const marker = new kakao.maps.Marker({
                    position: new kakao.maps.LatLng(lat, lon),
                });
                marker.setMap(map);


                // 인포 윈도우 표시
                const iwContent = `<div class='restaurant-name'>${name}</div>
                    <div class='restaurant-address'>${roadAddress}</div>
                `;
                const iwPosition = new kakao.maps.LatLng(lat, lon);

                const infoWindow = new kakao.maps.InfoWindow({
                    position: iwPosition,
                    content: iwContent,
                    removable: true,
                });

                infoWindow.open(map, marker);
            });
        }
};
window.addEventListener('DOMContentLoaded', function() {
    //commonLib.mapLib.init();
})