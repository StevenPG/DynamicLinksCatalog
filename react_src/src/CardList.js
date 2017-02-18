import React, { Component } from 'react';

// Individual Cards
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
// End Individual Cards

class CardList extends Component {

	state = {
    	cardArray: []
  	};

	componentWillMount() {

		// Build the array of cards with the card as input
		for(var i = 0; i < this.props.data['config']['cards'].length; i++){
			var currentCard = this.props.data['config']['cards'][i];
			this.state.cardArray.push(
				<div className="Card" key={i}>
		          <MuiThemeProvider>
		            <Card>
		                <CardHeader
		                  title={currentCard['cardheader']}
		                  subtitle={currentCard['headersubtitle']}
		                  avatar={currentCard['avatar']}
		                  actAsExpander={true}
		                  showExpandableButton={true}
		                />
		                <CardActions>
		                  <FlatButton label={currentCard['actions'][0]['buttontext']} />
		                  <FlatButton label={currentCard['actions'][1]['buttontext']} />
		                </CardActions>
		                <CardText expandable={true}>
		                  {currentCard['bodytext']}
		                </CardText>
		              </Card>
		          </MuiThemeProvider>
		        </div>
			);
		}

		console.log(this.props.data);
	}

	render() {
	    return (
			<div>
		    	{this.state.cardArray}
		    </div>)
	}
}

export default CardList;
