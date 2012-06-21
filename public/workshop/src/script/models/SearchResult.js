(function() {

  define(function() {
    var SearchResult;
    return SearchResult = (function() {

      function SearchResult(roomOffer, matchingScore, currentLat, currentLong) {
        this.roomOffer = roomOffer;
        this.matchingScore = matchingScore;
        this.currentLat = currentLat;
        this.currentLong = currentLong;
        if (this.roomOffer == null) {
          throw new Error('roomOffer is undefined!');
        }
        if (this.matchingScore == null) {
          throw new Error('matchingScore is undefined!');
        }
      }

      SearchResult.prototype.currentDistance = function() {
        var R, a, c, d, dLat, dLon, lat1, lat2, lon1, lon2;
        lat1 = this.roomOffer.flatshare.geoLocation.latitude;
        lon1 = this.roomOffer.flatshare.geoLocation.longitude;
        lat2 = this.currentLat;
        lon2 = this.currentLong;
        R = 6371;
        dLat = (lat2 - lat1) * Math.PI / 180;
        dLon = (lon2 - lon1) * Math.PI / 180;
        lat1 = lat1 * Math.PI / 180;
        lat2 = lat2 * Math.PI / 180;
        a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        d = R * c;
        return Math.round(d);
      };

      return SearchResult;

    })();
  });

}).call(this);
