import React, { Component } from 'react';

class CardList extends Component {

	state = {
    	cardArray: [],
  	};

	componentWillMount() {
		console.log("mounted component");
		this.setState({cardArray: this.props.data()})
	}

	componentWillReceiveProps(nextProps) {
		console.log("component will get props");
		// Run build function that was passed in
		this.setState({cardArray: this.props.data()})
	}

	render() {
	    return (
			<div>
		    	{this.state.cardArray}
		    </div>)
	}
}

export default CardList;
