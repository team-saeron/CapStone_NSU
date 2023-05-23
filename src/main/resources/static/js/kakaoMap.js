//지도 설정
const mapContainer = document.getElementById('map'),
	mapOption = {
	    center: new kakao.maps.LatLng(36.5,127.5),	// 지도의 중심 좌표(임의 설정)
	    level: 13					// 지도의 확대 레벨(임의 설정)
	};

//설정한 지도 생성
const map = new kakao.maps.Map(mapContainer, mapOption);

//마커 초기화(초기화 시 지도에 미리 지정 가능 : 카카오맵 API 문서 참조)
const marker = new kakao.maps.Marker();

//카카오맵 클릭 이벤트 추가
kakao.maps.event.addListener(map, 'click', (mouseEvent) => {
	//클릭한 위도, 경도 정보 불러오기
	const latlng = mouseEvent.latLng;
	//마커 위치를 클릭한 위치로 이동
	marker.setPosition(latlng);
	marker.setMap(map);

	alert(`위도 : ${latlng.getLat()}, 경도 : ${latlng.getLng()}`);
});