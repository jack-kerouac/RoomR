HTML5-Workshop - RoomR
======================
Hallo

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
    1. Das `play` Kommando muss im Systempfad liegen
  3. irgendein Git Client (<http://www.makeuseof.com/tag/5-windows-git-clients-git-job/>)
  4. [Node.js & NPM](http://nodejs.org/) >= 6.0.0
    1. Für Linux gibt's [hier](https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager) eine Anleitung
  5. [Phantom.js](http://www.phantomjs.org/)
    1. Der `bin`-Ordner muss im Systempfad liegen

Setup
-----

  1. `git clone git@github.com:jack-kerouac/RoomR.git`
  2. `play deps --sync`
  3. `play eclipsify`
  4. `play run`
  5. Die Anwendung läuft unter <http://localhost:9000/>, <http://localhost:9000/rest/users/1>
  6. in `public/workshops` `npm install` ausführen
  7. `./node_modules/grunt/bin/grunt` ausführen

Ordnerstruktur
--------------
  * `public/workshop`: Content von Peter. Dort liegt alles, was uns Peter schon bereitgestellt hat. Der Content
    von diesem Verzeichnis wird unter der Basis-URL ausgeliefert.
  * `conf/routes`: Routes file. Diese Datei konfiguriert, welche Ressourcen unter welcher URL erreichbar sind.
    Dort ist auch das REST-Interface beschrieben.
