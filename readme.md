HTML5-Workshop - RoomR
======================

Technologien im Workshop
------------------------

  1. [Grunt](https://github.com/cowboy/grunt) (Buildsystem auf Basis von Node.js)
  2. [RequireJS](http://requirejs.org/) (Scriptloader) + [AMD](https://github.com/amdjs/amdjs-api/wiki/AMD) (Modulsystem)
  3. [CoffeeScript](http://coffeescript.org/) (JS) und [Stylus](http://learnboost.github.com/stylus/) (CSS)
  4. [QUnit](http://docs.jquery.com/QUnit) (Testsystem) + [PhantomJS](http://phantomjs.org/) (Headless Browser)
  5. [BackboneJS](http://backbonejs.org/) (MVC) + [jQuery](http://jquery.com/) und [Underscore](http://underscorejs.org/)
  6. Ganz viel **HTML5** und **CSS3**

Systemvoraussetzungen
---------------------

  1. [Sun JDK 1.6](http://www.oracle.com/technetwork/java/javase/downloads/jdk-6u25-download-346242.html)
  2. [Play 1.2.5-RC4](http://download.playframework.org/releases/play-1.2.5-RC4.zip)
    1. Put `play` executable in your path 
  3. irgendein Git Client (<http://www.makeuseof.com/tag/5-windows-git-clients-git-job/>)
  4. [Node.js & NPM](http://nodejs.org/) >= 6.0.0
  5. [Phantom.js](http://www.phantomjs.org/) (`bin`-Ordner muss im Systempfad liegen)

Setup
-----

  1. `git clone git@github.com:jack-kerouac/RoomR.git`
  2. `play deps --sync`
  3. `play eclipsify`
  4. `play run`
  5. browse to <http://localhost:9000/>, <http://localhost:9000/rest/users/1>
  6. in `public/workshops` `npm install` ausfuehren
  7. `node_modules/grunt/bin/grunt` ausfuehren

Ordnerstruktur
--------------
HTML-Stuff: `public/workshop`
Routes file: `conf/routes`
