import React, { Component } from 'react';

// Manipulate HTML Head Information
import Helmet from "react-helmet";
// End Manipulate HTML Head Information

// Top Bar
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import Dialog from 'material-ui/Dialog';
import IconButton from 'material-ui/IconButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import FlatButton from 'material-ui/FlatButton';
// End Top Bar

// Loading bar
import LinearProgress from 'material-ui/LinearProgress';
// End Loading bar

// Snackbar
import Snackbar from 'material-ui/Snackbar';
// End Snackbar

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
    loading: false,
    hasValidation: false,
    isValidated: false,
    openAuth: false,
    username: "",
    password: "",
    serverConnected: true,
    showGoodSnack: false,
    showBadSnack: false,
  };

  // -- Refreshing the page --
  handleRefresh = () => {
  	var that = this;
  	this.setState({loading: true})
  	axios.get(window.location.href + 'config')
  	.then(function(response) {
  		that.setState({json: response['data']}, that.configureViaJSON);
  	})
  	.catch((error) => {
	    console.log(error.message);
	    console.log(error.code); // Not always specified
	    console.log(error.config); // The config that was used to make the request
	    console.log(error.response); // Only available if response was received from the server
	  });
    this.forceUpdate();
    this.setState({loading: false})
  }
  // -- End refresh method --

  // -- Settings functions --
  handleOpen = () => {

  	// If validated, open
    if(this.state.isValidated){
  		this.setState({open: true});
  	} 

  	// if connected to server but not validated, open validation
  	if(!this.state.isValidated && this.state.hasValidation){
  		this.setState({openAuth: true})
  	}
  };

  handleSave = () => {
  	this.setState({loading: true})
    this.setState({json: JSON.parse(this.state.jsonText)}, this.configureViaJSON);
    axios.post(window.location.href + 'config', JSON.parse(this.state.jsonText))
	  .catch((error) => {
	    console.log(error.message);
	    console.log(error.code); // Not always specified
	    console.log(error.config); // The config that was used to make the request
	    console.log(error.response); // Only available if response was received from the server
	  });
	this.setState({loading: false})
    this.handleClose();
  }

  // --- Snackbar functions ---
  // http://www.material-ui.com/#/components/snackbar
  openGoodCredsSnack = () => {
	this.setState({showGoodSnack: true})
  }

  openBadCredsSnack = () => {
	this.setState({showBadSnack: true})
  }

  closeGoodCredsSnack = () => {
  	this.setState({showGoodSnack: false})
  }

  closeBadCredsSnack = () => {
  	this.setState({showBadSnack: false})
  }
  // --- End snackbar functions ---

  onConfigChange = (e) => {
	  this.setState({loading: true})
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
	  this.setState({loading: false})
  }

  handleClose = () => {
	    this.setState({open: false});
	    this.setState({jsonerror: ""})
  };
  // -- End Settings functions --

  // Handle credential creation
  addCreds = () => {
  	var that = this;
  	axios.post(window.location.href + 'setup-security', 
  		document.getElementById("username").value + ":" + document.getElementById("password").value)
  	.then(function(response) {
  		if(response['status'] === 200){
  			that.setState({hasValidation: true});
  			that.setState({isValidated: true});
  			that.setState({SaveDisabled: true});
  		} else {
  			console.log("Something went wrong, please try again...");
  			that.setState({hasValidation: false});
  			that.setState({isValidated: false});
  		}
  	})
  	.catch((error) => {
  		console.log(error);
  		that.setState({hasValidation: false});
  		that.setState({isValidated: false});
	  });
  }
  // End credential creation

  // Handle checking creds
  checkCreds = () => {
  	var that = this;
  	axios.post(window.location.href + 'security', 
  		document.getElementById("check_username").value + ":" + document.getElementById("check_password").value)
  	.then(function(response) {
  		if(response['status'] === 200){
  			if(response['data']) { // If response is true
  				that.setState({hasValidation: true, isValidated: true, open: true, SaveDisabled: true})
  				that.openGoodCredsSnack();
  			} else {
  				that.setState({hasValidation: true, isValidated: false})
  				that.openBadCredsSnack();
  			}
  		} else if(response['status'] === 503) {
  			that.setState({hasValidation: false, isValidated: false})
  			console.log("Error communicating with server file system...");
  		}
  	})
  	.catch((error) => {
  		console.log(error);
	  });

  	// If server is not connected, allow any creds, also allow if creds are good
 	if(!this.serverConnected || true){
  		this.setState({openAuth: false})
  		this.setState({isValidated: true});
  	} else if(this.serverConnected && false){
		this.setState({openAuth: true})
  		this.setState({isValidated: false});
  		console.log("Credentials denied, try again");
  	}
  }
  // End handling cred check

  // Handle Settings Validation with Server
  checkValidation = () => {
  	// Check for pre-validation
  	if(this.state.isValidated){
  		return true;
  	}
  }
  // End Handling Settings validation with Server

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
		                  <FlatButton 
		                  	label={currentCard['Buttons'][0]['buttontext']} 
		                  	href={currentCard['Buttons'][0]['linkURL']}
		                  	/>
		                  <FlatButton 
		                  	label={currentCard['Buttons'][1]['buttontext']} 
		                  	href={currentCard['Buttons'][1]['linkURL']}
		                  	/>
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
    this.setState({
      json: {
        "config": {
          "PageTitle": "Default Dynamic Links Catalog",
          "PageFavicon": "https://pbs.twimg.com/profile_images/571295883073843200/OerZFKD_.png",
          "DirectoryTitle" : "ExampleTitle",
          "DirectoryHexColor" : "#000000",
          "PageBackgroundURL" : "/img/default_background.jpg",
          "cards" : [
            {
              "Header" : "ExampleHeader",
              "HeaderSubtitle" : "ExampleSubtitle",
              "ExpandedText" : "ExampleTextBody",
              "Image" : "https://upload.wikimedia.org/wikipedia/commons/8/84/Example.svg",
              "Buttons" : [
                  {
                    "buttontext" : "ExampleButtonText",
                    "linkURL" : ""
                  },
                  {
                    "buttontext" : "ExampleButtonText2",
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
  		that.setState({json: response['data']}, that.configureViaJSON);
  	})
  	 .catch((error) => {
	    console.log(error.message);
	    console.log(error.response.data);
	  })

  	// Find out if hasValidation should be true or false using AXIOS
  	axios.get(window.location.href + 'security')
  	.then(function(response) {
  		// success means server is connected
  		if(response['status'] === 200) {
  			that.setState({hasValidation: true, isValidated: false})
  		} else if(response['status'] === 204) {
			that.setState({hasValidation: false, isValidated: false})
  		} else if(response['status'] === 503) {
  			that.setState({hasValidation: false, isValidated: false})
  			console.log("Error communicating with server file system...");
  		}
  	})
  	.catch((error) => {
  		// failure means server isn't connected
  		that.setState({hasValidation: false, serverConnected: false})
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

    const securityActions = [
      <FlatButton
        label="Setup Authentication"
        primary={true}
        onTouchTap={this.addCreds}
      />
    ];

    const authActions = [
    	<FlatButton
        label="Authenticate"
        primary={true}
        onTouchTap={this.checkCreds}
      />
    ]
    
    var currentFavicon = this.state.json['config']['PageFavicon'];

    return (
      <div>
      	<Helmet
      		title={this.state.json['config']['PageTitle']}
      		link={
      			[
      				{
      					rel: 'icon', 
      					type: 'image/png', 
      					href: currentFavicon,
      				},
      			]
      		}
      	/>
        <div className="App">
          <MuiThemeProvider>
	            <AppBar
	            title={this.state.json['config']['DirectoryTitle']}
	            style={{backgroundColor: this.state.json['config']['DirectoryHexColor']}}
	            iconElementLeft={<IconButton onTouchTap={this.handleRefresh}><NavigationRefresh /></IconButton>}
	            iconElementRight={<FlatButton label="Settings" onTouchTap={this.handleOpen}/>}
	            />
            </MuiThemeProvider>
            <MuiThemeProvider>
            	{ this.state.loading ? <LinearProgress mode="indeterminate" /> : null }
          </MuiThemeProvider>
        </div>
        <div>
        	<MuiThemeProvider>
	        	<Snackbar
		          open={this.state.showGoodSnack}
		          message="Credentials Accepted"
		          autoHideDuration={6000}
		          onRequestClose={this.closeGoodCredsSnack}
		        />
	        </MuiThemeProvider>
	        <MuiThemeProvider>
	        	<Snackbar
		          open={this.state.showBadSnack}
		          message="Credentials Not Accepted: Incorrect username or password"
		          autoHideDuration={6000}
		          onRequestClose={this.closeBadCredsSnack}
		        />
	        </MuiThemeProvider>
        </div>
        <div>
          <MuiThemeProvider>
	          <Dialog
	              title="Required Authentication Setup, Please Enter New Admin Credentials"
	              actions={securityActions}
	              modal={true}
	              open={!this.state.hasValidation}
	              contentStyle={{position: 'absolute', left: '50%', top: '50%', transform: 'translate(-50%, -50%)'}}
	              repositionOnUpdate={ false }>
	            <TextField
	                id="username"
	                fullWidth={true}
	                hintText="Enter a username here"
	                floatingLabelText="Username"
	            />
	            <TextField
	                id="password"
	                type="password"
	                fullWidth={true}
	                hintText="Enter a password here"
	                floatingLabelText="Password"
	            />
	          </Dialog>
	      </MuiThemeProvider>
	      <MuiThemeProvider>
	          <Dialog
	              title="Authenticate to Make Changes"
	              actions={authActions}
	              modal={true}
	              open={this.state.hasValidation && !this.state.isValidated && this.state.openAuth}
	              contentStyle={{position: 'absolute', left: '50%', top: '50%', transform: 'translate(-50%, -50%)'}}
	              repositionOnUpdate={ false }>
	            <TextField
	                id="check_username"
	                fullWidth={true}
	                hintText="Enter a username here"
	                floatingLabelText="Username"
	            />
	            <TextField
	                id="check_password"
	                type="password"
	                fullWidth={true}
	                hintText="Enter a password here"
	                floatingLabelText="Password"
	            />
	          </Dialog>
          </MuiThemeProvider>
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
          <p>DynamicLinksCatalog V1.3.1; Steven Gantz, MIT (c) 2017. <a href="https://gitlab.com/StevenPG/DynamicLinksCatalog">Repository</a></p>
        </div>
      </div>
    );
  }
}

export default App;
