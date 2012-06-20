(function() {

  define(function() {
    'use strict';
    return function(priority) {
      switch (priority) {
        case 0:
          return 'Niedrig';
        case 1:
          return 'Normal';
        case 2:
          return 'Hoch';
      }
    };
  });

}).call(this);
