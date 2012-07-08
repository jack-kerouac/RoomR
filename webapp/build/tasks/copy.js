module.exports = function(grunt) {

	grunt.registerMultiTask( "copy", "Copy files to destination folder", function() {
		var src = this.data.src;
		var files = this.data.files ? this.data.files : '**/*';
		var allFiles = grunt.file.expandFiles(src  + '/' + files);
		var target = this.data.dest + '/';

		allFiles.forEach(function( fileName ) {
			var targetFile = target + fileName.replace(src + '/', '');
			grunt.file.copy( fileName, targetFile);
		});
		grunt.log.writeln( "copied " + allFiles.length + " files (" + src  + '/' + files + ") to " + target);
	});

};