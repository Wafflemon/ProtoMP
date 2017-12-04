# IsoSurfaceExample
This demonstrates a multiplayer implementation for some of the JME3 extensions (https://github.com/Simsilica.)
A modular architecture is used to separate the server and client implementations. This is just for fun and to
add to the Simsilica work.

## Feature Roadmap

- Main Menu
- Singleplayer Game
- Infinite Voxel Terrain
- Server
- Chat System
- Dedicated Server
- Zoned Voxel Synchronization
- Zoned Entity Synchronization
- VR Client Module

## Dependencies

A few dependencies have been included in the lib/. Those projects aren't available to be downloaded by Gradle
and were built manually. The particular JME3 version is https://github.com/jMonkeyEngine/jmonkeyengine/commit/58a92118797a23acec3061a376682a5c33868432.
Once these projects are checked into bintray or wherever, then these dependencies will be able to be removed.