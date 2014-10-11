## Cytoscape to D3.js Exporter


![](http://cl.ly/Xk5o/cytoscape-flat-logo-orange-100.png) ![](http://cl.ly/XkMY/d3logo.svg)

[![Build Status](https://travis-ci.org/keiono/cytoscape-d3.svg)](https://travis-ci.org/keiono/cytoscape-d3)
[![Coverage Status](https://img.shields.io/coveralls/keiono/cytoscape-d3.svg)](https://coveralls.io/r/keiono/cytoscape-d3)

## Release history
* 9/28/2014 - Version 1.0.2 released.  Minor fixes only (no new features).
* 9/15/2014 - Version 1.0.1 released.  Node locations (x, y) will be added as node property when user select Network and View.
 

## What is this for?
This is a [Cytoscape](http://www.cytoscape.org/) app for exporting network and data table to [D3.js](http://d3js.org/) style JSON.  D3.js is a JavaScript library for data visualization, and some of its preset layouts support network/tree data structure.  This app directly creates JSON file for those presets from Cytoscape network and data tables.

Currently, this App supports:

* Network JSON for force layout ([example](http://bl.ocks.org/mbostock/4062045))
* Tree JSON ([example](http://mbostock.github.io/d3/talk/20111018/tree.html))

## Quick Start Guide

### Installing App

#### From App Store
1. Open __*Apps &rarr; App Manager*__
1. Select D3.js Exporter and click _Install_


#### From Source
Minimum requirment to build this app is JDK 6 and Maven 3.

1. Clone this repository
1. Build App:
    ```
    mvn clean install
    ```
1. Copy the created JAR file to _~/CytoscapeConfiguration/3/app/installed/_ directory

### How to Use
To export current network and its table as D3.js style JSON, simply select a export menu:

* __*File &rarr; Export &rarr; Network...*__ - without (x,y) position of nodes
* __*File &rarr; Export &rarr; Network and View...*__ - with (x,y) position of nodes

#### Note
To export your data as tree, you need to select a root node manually (simply click the root node before exporting).

