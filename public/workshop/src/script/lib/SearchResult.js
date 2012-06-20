(function() {

  define(function() {
    var SearchResult;
    return SearchResult = (function() {

      function SearchResult(offer, matchingScore) {
        this.offer = offer;
        this.matchingScore = matchingScore;
        if (this.offer == null) {
          throw new Error('offer is undefined!');
        }
        if (this.matchingScore == null) {
          throw new Error('matchingScore is undefined!');
        }
      }

      return SearchResult;

    })();
  });

}).call(this);
