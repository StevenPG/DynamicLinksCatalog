import React, { Component } from 'react';

// Top Bar
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import Dialog from 'material-ui/Dialog';
import IconButton from 'material-ui/IconButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import FlatButton from 'material-ui/FlatButton';
// End Top Bar

// CardList
import CardList from './CardList'
// End CardList

import './App.css';

// Needed for onTouchTap
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();

class App extends Component {

  state = {
    open: false,
    json: [],
  };

  // -- Refreshing the page --
  handleRefresh = () => {
    this.forceUpdate();
  }
  // -- End refresh method --

  // -- Settings functions --
  handleOpen = () => {
    this.setState({open: true});
  };

  handleClose = () => {
    this.setState({open: false});
  };
  // -- End Settings functions --

  // Run when component is rendering
  // Retrieve JSON info for current and child components
  componentWillMount() {
    var json = {
      "config": {
        "pagetitle" : "title",
        "cards" : [
          {
            "cardheader" : "FirstHeader",
            "headersubtitle" : "FirstHeaderSubtitle",
            "bodytext" : "FirstTextBody",
            "avatar" : "http://fresnostate.edu/webresources/images/128x128/128x128-youtube.png",
            "actions" : [
                {
                  "buttontext" : "FirstButtonText",
                  "url" : "url"
                },
                {
                  "buttontext" : "SecondButtonText",
                  "url" : "url"
                }
              ]
          },
          {
            "cardheader" : "SecondHeader",
            "headersubtitle" : "SecondHeaderSubtitle",
            "bodytext" : "SecondTextBody",
            "avatar" : "http://fresnostate.edu/webresources/images/128x128/128x128-youtube.png",
            "actions" : [
                {
                  "buttontext" : "FirstButtonText",
                  "url" : "url"
                },
                {
                  "buttontext" : "SecondButtonText",
                  "url" : "url"
                }
              ]
          }
          ]
      }
    }
    this.setState({
      json: {
        "config": {
          "pagetitle" : "title",
          "cards" : [
            {
              "cardheader" : "FirstHeader",
              "headersubtitle" : "FirstHeaderSubtitle",
              "bodytext" : "FirstTextBody",
              "avatar" : "http://fresnostate.edu/webresources/images/128x128/128x128-youtube.png",
              "actions" : [
                  {
                    "buttontext" : "FirstButtonText",
                    "url" : "url"
                  },
                  {
                    "buttontext" : "SecondButtonText",
                    "url" : "url"
                  }
                ]
            },
            {
              "cardheader" : "SecondHeader",
              "headersubtitle" : "SecondHeaderSubtitle",
              "bodytext" : "SecondTextBody",
              "avatar" : "http://fresnostate.edu/webresources/images/128x128/128x128-youtube.png",
              "actions" : [
                  {
                    "buttontext" : "FirstButtonText",
                    "url" : "url"
                  },
                  {
                    "buttontext" : "SecondButtonTextTest",
                    "url" : "url"
                  }
                ]
            }
            ]
        }
      }
    });
  }

  render() {

    // Dialog actions externalized
    const actions = [
      <FlatButton
        label="Cancel"
        primary={true}
        onTouchTap={this.handleClose}
      />,
      <FlatButton
        label="Save"
        primary={true}
        keyboardFocused={true}
        onTouchTap={this.handleClose}
      />,
    ];

    return (
      <div>
        <div className="App">
          <MuiThemeProvider>
            <AppBar
            title="Title"
            style={{backgroundColor: '#EA5D5E'}}
            iconElementLeft={<IconButton><NavigationRefresh /></IconButton>}
            iconElementRight={<FlatButton label="Settings" onTouchTap={this.handleOpen}/>}
            />
          </MuiThemeProvider>
          <MuiThemeProvider>
            <Dialog
              title="Application Settings"
              actions={actions}
              modal={false}
              open={this.state.open}
              onRequestClose={this.handleClose}>
              This Modal will soon contain the application settings, along with simple authentication.
            </Dialog>
          </MuiThemeProvider>
        </div>
        <div className="CardList">
          <CardList data={this.state.json}/>
        </div>
      </div>
    );
  }
}

export default App;
