Cytoscape to D3.js Exporter
============
## Release history
* 9/15/2014 - Version 1.0.1 released.  Node locations (x, y) will be added as node property when user select Network and View.
 

## What is this for?
This is a [Cytoscape](http://www.cytoscape.org/) App for exporting network and its data table to [D3.js](http://d3js.org/) compatible JSON.  D3.js is a general data visualization JavaScript library, and some of its presets support network/tree style data.  This App directly creates JSON file for those presets from Cytoscape network and data tables.

Currently, this App supports:

* Network JSON for force layout ([example](http://bl.ocks.org/mbostock/4062045))
* Tree JSON ([example](http://mbostock.github.io/d3/talk/20111018/tree.html))

## How to Use
Build, and install the JAR file from Cytoscape's App manager or simply install from Cytoscape App Store.  You can export your network and its data table as JSON from *File &rarr; Export &rarr; Network*.

**For Tree data, you need to select root node manually (simply click the root node before exporting)**.

