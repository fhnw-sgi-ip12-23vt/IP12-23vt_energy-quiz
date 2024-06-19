$#!/usr/bin/env bash
xdotool windowactivate $(xdotool getactivewindow)
xdotool search --sync --name "Energie App 0.1" windowmove 0 -30
xdotool search --sync --name "Energie App 0.1" windowsize 1600 600