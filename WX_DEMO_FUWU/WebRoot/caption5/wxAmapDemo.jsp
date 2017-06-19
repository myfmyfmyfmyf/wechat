<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!-- @author Ĳ�Ʒ�  -->
<html>
<head>
<title>΢��JS-SDK����</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<body>
	<div style="width:100%;height:30px;" align="center">�ߵµ�ͼ�㼯��</div>
	
    <div id="container" style="width:100%;height:90%;"></div>

<div class="button-group" style="width:100%;height:30px;">
    <input type="button"  value="�Զ�����ʽ��ۺ�" id="add1"/>
    <input type="button"  value="Ĭ����ʽ��ۺ�" id="add0"/>
</div>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=0e5deb9a9d82f460c2e6590182e71239"></script>
<script>
    var cluster, markers = [];
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],
        zoom: 13
    });

    // ������ͼ���500����ע��
    var mapBounds = map.getBounds();
    var sw = mapBounds.getSouthWest();
    var ne = mapBounds.getNorthEast();
    var lngSpan = Math.abs(sw.lng - ne.lng);
    var latSpan = Math.abs(ne.lat - sw.lat);
    for (var i = 0; i < 500; i++) {
        var markerPosition = [sw.lng + lngSpan * (Math.random() * 1), ne.lat - latSpan * (Math.random() * 1)];
        var marker = new AMap.Marker({
            position: markerPosition,
            icon: "http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/img/marker.png",
            offset: {x: -8,y: -34}
        });
        markers.push(marker);
    }
    addCluster(0);

    AMap.event.addDomListener(document.getElementById('add0'), 'click', function() {
        addCluster(0);
    });
    AMap.event.addDomListener(document.getElementById('add1'), 'click', function() {
        addCluster(1);
    });

    // ��ӵ�ۺ�
    function addCluster(tag) {
        if (cluster) {
            cluster.setMap(null);
        }
        if (tag == 1) {
            var sts = [{
                url: "http://a.amap.com/lbs/static/img/1102-1.png",
                size: new AMap.Size(32, 32),
                offset: new AMap.Pixel(-16, -30)
            }, {
                url: "http://a.amap.com/lbs/static/img/2.png",
                size: new AMap.Size(32, 32),
                offset: new AMap.Pixel(-16, -30)
            }, {
                url: "http://lbs.amap.com/wp-content/uploads/2014/06/3.png",
                size: new AMap.Size(48, 48),
                offset: new AMap.Pixel(-24, -45),
                textColor: '#CC0066'
            }];
            map.plugin(["AMap.MarkerClusterer"], function() {
                cluster = new AMap.MarkerClusterer(map, markers, {
                    styles: sts
                });
            });
        } else {
            map.plugin(["AMap.MarkerClusterer"], function() {
                cluster = new AMap.MarkerClusterer(map, markers);
            });
        }
    }
</script>
</body>
</html>
