# rendercitygml

This project supports the rendering of citygml dataset.

Key Features:
- It uses 3dCityDB as it's backend database.
- Implements tile based rendering, thus dynamic loading and un loading of building data depending on the camera poistion. This gives more response time, thus enhancing performance.
- Textures are not supported as for now, will be supported in the furture. => This is now supported.

Setup:
- Setup 3DCityDB on top of postgis.
- Need to run two scripts for creating support for tile_geometry and tile_mapping. The script can be found in src/dao/
- import the data into 3DCityDB.
- run the main function in rendercitygml/src/graphics/renderFrame.java

Results:
[![Building Renderering](http://img.youtube.com/vi/G9VD53Oj06A/0.jpg)](https://youtu.be/G9VD53Oj06A)
