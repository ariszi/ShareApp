
Share android client
==================================
In this documentation i m going to mentions some of the architectures, frameworks, libraries, conventions, etc that
i m going to use in order to complete this project.

# Commit convention Overview
The commits will be structured with the format of [Purpose]: [Description]
The purpose will have a wide variety on key works like feature, refactor, fix, tests, etc.

# Architecture Overview
This project will use a reactive approach for its presentation layer. It will observe sources of the state, it will react
on their changes, and it will create a state from the combination of those reaction(s). This approach can handle
the projects' requirements.
(This approach in bigger projects might be a bottleneck since the combine function can take up to 5 items. <-- Debatable)

# Build instructions
You might need to download Android Tiramisu from SDK manager if you get a message for Android 33 issues.
Sync gradle files and restart your studio.

# Implementation notes
 The priorities on this project are given according to the specs guidelines and personal preference.
 Unfortunately the time is not unlimited in order to sharpen all edges so some things like code styles
 or forgotten/unused code/libs here and there might exist.

# Final notes
Things for future improvement. (Ordered by priority)
 - Optimise dependencies in modules
 - Convention plugin
 - TOML
 - More tests (2 tests were created as indication and example of other similar classes)
 - UI tests
 - Compose
 - Compose nav (this would make my life easier on this implementation)
 - Fresh up the UI 
 - Animations
 - Code analysis tool


