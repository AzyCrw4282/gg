del /F /Q build
mkdir build
mkdir build/js/
robocopy . build index.html 
robocopy . build favicon.ico
robocopy . build scene.babylon.manifest
robocopy textures build/textures /e
robocopy sounds build/sounds
robocopy css build/css
robocopy js/babylon build/js/babylon
rollup js/App.js --o build/js/App.js --f es
