import React, { Component } from 'react';

// Top bar
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import Dialog from 'material-ui/Dialog';
import IconButton from 'material-ui/IconButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';

// Individual cards
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';

import './App.css';

// Needed for onTouchTap
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();

class App extends Component {

  state = {
    open: false,
  };

  // Refreshing the page
  handleRefresh = () => {
    this.forceUpdate();
  }
  // End refresh method

  // Settings functions
  handleOpen = () => {
    this.setState({open: true});
  };

  handleClose = () => {
    this.setState({open: false});
  };
  // End Settings functions

  render() {
    return (
      <div>
        <div className="App">
          <MuiThemeProvider>
            <AppBar
            title="Title"
            iconElementLeft={<IconButton><NavigationRefresh /></IconButton>}
            iconElementRight={<FlatButton label="Settings" onTouchTap={this.handleOpen}/>}
            />
          </MuiThemeProvider>
          <MuiThemeProvider>
            <Dialog
              title="Application Settings"
              actions={ <FlatButton label="Cancel" primary={true} onTouchTap={this.handleClose} />}
              modal={false}
              open={this.state.open}
              onRequestClose={this.handleClose}>
              This Modal will soon contain the application settings, along with simple authentication.
            </Dialog>
          </MuiThemeProvider>
        </div>
        <div className="Card">
          <MuiThemeProvider>
            <Card>
                <CardHeader
                  title="Title"
                  subtitle="Subtitle"
                  avatar="http://fresnostate.edu/webresources/images/128x128/128x128-youtube.png"
                  actAsExpander={true}
                  showExpandableButton={true}
                />
                <CardActions>
                  <FlatButton label="Action1" />
                  <FlatButton label="Action2" />
                </CardActions>
                <CardText expandable={true}>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                  Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
                  Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
                  Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
                </CardText>
              </Card>
          </MuiThemeProvider>
        </div>
      </div>
    );
  }
}

export default App;
