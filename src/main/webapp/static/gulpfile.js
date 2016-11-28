
'use strict';

var gulp = require('gulp'),  
	sass = require("gulp-sass"),
    notify = require("gulp-notify"),
    bower = require('gulp-bower'),
    uglify = require('gulp-uglify'),
    pump = require('pump'),
    cleanCSS = require('gulp-clean-css'),
    concat = require('gulp-concat');

var config = {
	sassPath: './sass',
	cssPath: './css',
	minifiedJSPath: './min-js/',
	bowerDir: './bower_components'
}

gulp.task('bower', function() {
    return bower()
        .pipe(gulp.dest(config.bowerDir))
});

gulp.task('sass', function() {
    return gulp.src('./sass/**/*.scss')
	    .pipe(sass().on('error', sass.logError))
	    .pipe(gulp.dest(config.cssPath));
});

gulp.task('minify-css', function() {
	  return gulp.src('./css/**/*.css')
	    .pipe(cleanCSS({compatibility: 'ie8'}))
	    .pipe(gulp.dest(config.cssPath));
	});

gulp.task('minify-js', function (cb) {
  pump([
        gulp.src('./js/**/*.js'),
        uglify(),
        gulp.dest(config.minifiedJSPath)
    ],
    cb
  );
});

gulp.task('all-in-one-app-script', function() {
	  return gulp.src('./js/**/*.js')
	    .pipe(concat('app.js'))
	    .pipe(gulp.dest('./min-js/'));
	});

gulp.task('all-in-one-vendor-script', function() {
	  return gulp.src('./bower_components/**/*.js')
	    .pipe(concat('vendor.js'))
	    .pipe(gulp.dest('./min-js/'));
	});

gulp.task('default', [
                      'bower', 
                      'sass', 
//                      'minify-js', 
                      'minify-css', 
                      'all-in-one-app-script',
//                      'all-in-one-vendor-script'
                      ]);