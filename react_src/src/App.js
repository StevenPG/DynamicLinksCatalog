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
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
// End CardList

// Settings options
import TextField from 'material-ui/TextField';
// End Settings options

// HTTP library
import axios from 'axios';
// End HTTP library

import './App.css';

// Needed for onTouchTap
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();

class App extends Component {

  state = {
    open: false,
    json: [],
    jsonText: "",
    SaveDisabled: false,
    jsonerror: "",
  };

  // -- Refreshing the page --
  handleRefresh = () => {
  	var that = this;
  	axios.get(window.location.href + 'config')
  	.then(function(response) {
  		console.log(response['data']);
  		that.setState({json: response['data']}, that.configureViaJSON);
  	})
  	.catch(function(error) {
  		console.log(error);
  	});
    this.forceUpdate();
  }
  // -- End refresh method --

  // -- Settings functions --
  handleOpen = () => {
    this.setState({open: true});

    // Disable save button unless something changes (in settings)
    this.setState({SaveDisabled: true})
  };

  handleSave = () => {
    this.setState({json: JSON.parse(this.state.jsonText)}, this.configureViaJSON);
    axios.post(window.location.href + 'config', JSON.parse(this.state.jsonText))
	  .then(function (response) {
	    console.log(response);
	  })
	  .catch(function (error) {
	    console.log(error);
	});
    this.handleClose();
  }

  onConfigChange = (e) => {
  try {
    JSON.parse(e.target.value);
  } catch (err) {
    this.setState({SaveDisabled: true})
    this.setState({jsonerror: "Invalid JSON..."})
    return;
  }
    this.setState({jsonText: e.target.value})
    this.setState({jsonerror: ""})
    this.setState({SaveDisabled: false})
  }

  handleClose = () => {
    this.setState({open: false});
    this.setState({jsonerror: ""})
  };
  // -- End Settings functions --

  // Function builds cardlist directly
  buildCardArray = () => {
		// Build the array of cards with the card as input
		var tmpCardArray = [];
		for(var i = 0; i < this.state.json['config']['cards'].length; i++){
			var currentCard = this.state.json['config']['cards'][i];
			//this.state.cardArray.push(
			tmpCardArray.push(
				<div className="Card" key={i}>
		          <MuiThemeProvider>
		            <Card>
		                <CardHeader
		                  title={currentCard['Header']}
		                  subtitle={currentCard['HeaderSubtitle']}
		                  avatar={currentCard['Image']}
		                  actAsExpander={true}
		                  showExpandableButton={true}
		                />
		                <CardActions>
		                  <FlatButton label={currentCard['Buttons'][0]['buttontext']} />
		                  <FlatButton label={currentCard['Buttons'][1]['buttontext']} />
		                </CardActions>
		                <CardText expandable={true}>
		                  {currentCard['ExpandedText']}
		                </CardText>
		              </Card>
		          </MuiThemeProvider>
		        </div>
			);
		}
		return tmpCardArray;
	}
	// End cardlist build function

  // Callback for data setState
  configureViaJSON = () => {
  	var backgroundURL = this.state.json['config']['PageBackgroundURL'];
    document.body.style.background = 'url(' + backgroundURL + ') no-repeat center center fixed';
    document.body.style.background = 'cover';
  }
  // End Callback



  // Run when component is first rendering
  // Retrieve JSON info for current and child components
  componentWillMount() {
  	console.log("Setting Initial View State")
    this.setState({
      json: {
        "config": {
          "DirectoryTitle" : "",
          "DirectoryHexColor" : "",
          "PageBackgroundURL" : "",
          "cards" : [
            {
              "Header" : "",
              "HeaderSubtitle" : "",
              "ExpandedText" : "",
              "Image" : "",
              "Buttons" : [
                  {
                    "buttontext" : "",
                    "linkURL" : ""
                  },
                  {
                    "buttontext" : "",
                    "linkURL" : ""
                  }
                ]
            }
          ]
        }
      }
    }, this.configureViaJSON);

    var that = this;
  	axios.get(window.location.href + 'config')
  	.then(function(response) {
  		console.log(response['data']);
  		that.setState({json: response['data']}, that.configureViaJSON);
  	})
  	.catch(function(error) {
  		console.log(error);
  	});
  }

  render() {

    // Dialog actions externalized
    const actions = [
      <FlatButton
        label="Cancel"
        primary={true}
        keyboardFocused={true}
        onTouchTap={this.handleClose}
      />,
      <FlatButton
        label="Save"
        primary={true}
        disabled={this.state.SaveDisabled}
        onTouchTap={this.handleSave}
      />,
    ];

    return (
      <div>
        <div className="App">
          <MuiThemeProvider>
            <AppBar
            title={this.state.json['config']['DirectoryTitle']}
            style={{backgroundColor: this.state.json['config']['DirectoryHexColor']}}
            iconElementLeft={<IconButton onTouchTap={this.handleRefresh}><NavigationRefresh /></IconButton>}
            iconElementRight={<FlatButton label="Settings" onTouchTap={this.handleOpen}/>}
            />
          </MuiThemeProvider>
        </div>
        <div>
          <MuiThemeProvider>
            <Dialog
              title="Application Configuration"
              actions={actions}
              modal={false}
              open={this.state.open}
              contentStyle={{position: 'absolute', left: '50%', top: '50%', transform: 'translate(-50%, -50%)'}}
              repositionOnUpdate={ false }
              onRequestClose={this.handleClose}>
              	
                <TextField
                id="jsonconfig"
                multiLine={true}
                onChange={this.onConfigChange}
                fullWidth={true}
                errorText={this.state.jsonerror}
                defaultValue={JSON.stringify(this.state.json, null , "\t")}
              /><br />
            </Dialog>
          </MuiThemeProvider>
        </div>
        <div className="CardList">
          <CardList data={this.buildCardArray}/>
        </div>
        <div className="footer">
          <p>DynamicLinksCatalog V1.2.2; Steven Gantz, GPL-3 (C) 2017. <a href="https://gitlab.com/StevenPG/DynamicLinksCatalog">Repository</a></p>
        </div>
      </div>
    );
  }
}

export default App;
