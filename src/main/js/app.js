'use strict';

const React = require('react');
const ReactDOM = require('react-dom')
const when = require('when');
const client = require('./client');
const follow = require('./follow'); // function to hop multiple links by "rel"


const root = '/api';
const searchRoot = '/api/employees/search';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {employees: [], attributes: [], pageSize: 10, searchBox: '', links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.updateSearchBox = this.updateSearchBox.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
	}

	// tag::follow-2[]
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'employees', params: {size: pageSize}}]
		).then(employeeCollection => {
			return client({
				method: 'GET',
				path: employeeCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				this.links = employeeCollection.entity._links;
				return employeeCollection;
			});
		}).then(employeeCollection => {
			return employeeCollection.entity._embedded.employees.map(employee =>
					client({
						method: 'GET',
						path: employee._links.self.href
					})
			);
		}).then(employeePromises => {
			return when.all(employeePromises);
		}).done(employees => {
			this.setState({
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				searchBox: searchBox,
				links: this.links
			});
		});
	}

	// tag::follow-2[]
    searchFromServer(searchBox,pageSize) {
    	follow(client, searchRoot, [
    			{rel: 'findEverything', params: {criteria: searchBox,size: pageSize}}]
    	).then(employeeCollection => {
    		return employeeCollection.entity._embedded.employees.map(employee =>
    				client({
    					method: 'GET',
    					path: employee._links.self.href
    				})
    		);
    	}).then(employeePromises => {
    		return when.all(employeePromises);
    	}).done(employees => {
    		this.setState({
    			employees: employees,
    			attributes: Object.keys(this.schema.properties),
    			pageSize: pageSize,
    			searchBox: searchBox,
    			links: this.links
    		});
    	});
    }
	// end::follow-2[]

	// tag::create[]
	onCreate(newEmployee) {
		var self = this;
		follow(client, root, ['employees']).then(response => {
			return client({
				method: 'POST',
				path: response.entity._links.self.href,
				entity: newEmployee,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [{rel: 'employees', params: {'size': self.state.pageSize}}]);
		}).done(response => {
			if (typeof response.entity._links.last != "undefined") {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		});
	}
	// end::create[]

	// tag::update[]
	onUpdate(employee, updatedEmployee) {
		client({
			method: 'PUT',
			path: employee.entity._links.self.href,
			entity: updatedEmployee,
			headers: {
				'Content-Type': 'application/json',
				'If-Match': employee.headers.Etag
			}
		}).done(response => {
			this.loadFromServer(this.state.pageSize);
		}, response => {
			if (response.status.code === 412) {
				alert('DENIED: Unable to update ' +
					employee.entity._links.self.href + '. Your copy is stale.');
			}
		});
	}
	// end::update[]

	// tag::delete[]
	onDelete(employee) {
		client({method: 'DELETE', path: employee.entity._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}
	// end::delete[]

	// tag::navigate[]
	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(employeeCollection => {
			this.links = employeeCollection.entity._links;

			return employeeCollection.entity._embedded.employees.map(employee =>
					client({
						method: 'GET',
						path: employee._links.self.href
					})
			);
		}).then(employeePromises => {
			return when.all(employeePromises);
		}).done(employees => {
			this.setState({
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				searchBox: this.state.searchBox,
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}
	// end::navigate[]

	// tag::update-page-size[]
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}
	// end::update-page-size[]
	updateSearchBox(searchBox, pageSize) {
    	if (searchBox !== this.state.searchBox) {
    	    if(searchBox.length > 3){  //Check for at least 2 caracters
    		    this.searchFromServer(searchBox,pageSize);
    		}
    	}
    }

	// tag::follow-1[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}
	// end::follow-1[]

	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<EmployeeList employees={this.state.employees}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  searchBox={this.state.searchBox}
							  attributes={this.state.attributes}
							  onNavigate={this.onNavigate}
							  onUpdate={this.onUpdate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}
							  updateSearchBox={this.updateSearchBox}/>
			</div>
		)
	}
}

// tag::create-dialog[]
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		var newEmployee = {};
		newEmployee["firstName"] = ReactDOM.findDOMNode(this.refs["firstName"]).value.trim();
		newEmployee["middleName"] = ReactDOM.findDOMNode(this.refs["middleName"]).value.trim();
		newEmployee["lastName"] = ReactDOM.findDOMNode(this.refs["lastName"]).value.trim();
		newEmployee["email"] = ReactDOM.findDOMNode(this.refs["email"]).value.trim();
		newEmployee["jobTitle"] = ReactDOM.findDOMNode(this.refs["jobTitle"]).value.trim();



		this.props.onCreate(newEmployee);
		ReactDOM.findDOMNode(this.refs["firstName"]).value = '';
        ReactDOM.findDOMNode(this.refs["middleName"]).value = '';
        ReactDOM.findDOMNode(this.refs["lastName"]).value = '';
        ReactDOM.findDOMNode(this.refs["email"]).value = '';
        ReactDOM.findDOMNode(this.refs["jobTitle"]).value = '';

		window.location = "#";
	}

	render() {
		var inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field" />
			</p>
		);
		return (
			<div>
				<a href="#createEmployee">Hire</a>

				<div id="createEmployee" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new employee</h2>

						<form>
						    <p>
                        		<input type="text" placeholder={"firstName"} ref={"firstName"} className="field" />
                      		</p>
                      		<p>
                            	<input type="text" placeholder={"middleName"} ref={"middleName"} className="field" />
                       		</p>
                        	<p>
                   				<input type="text" placeholder={"lastName"} ref={"lastName"} className="field" />
                            </p>
                            <p>
                                <input type="text" placeholder={"email"} ref={"email"} className="field" />
                             </p>
                             <p>
                                <input type="text" placeholder={"jobTitle"} ref={"jobTitle"} className="field" />
                             </p>
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
};
// end::create-dialog[]

// tag::update-dialog[]
class UpdateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		var updatedEmployee = {};
		updatedEmployee["firstName"] = ReactDOM.findDOMNode(this.refs["firstName"]).value.trim();
		updatedEmployee["middleName"] = ReactDOM.findDOMNode(this.refs["middleName"]).value.trim();
		updatedEmployee["lastName"] = ReactDOM.findDOMNode(this.refs["lastName"]).value.trim();
		updatedEmployee["email"] = ReactDOM.findDOMNode(this.refs["email"]).value.trim();
        updatedEmployee["employeeStatus"] = ReactDOM.findDOMNode(this.refs["employeeStatus"]).value.trim();

		//updatedEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();

		this.props.onUpdate(this.props.employee, updatedEmployee);
		window.location = "#";
	}

	render() {
		var inputs = this.props.attributes.map(attribute =>
				<p key={this.props.employee.entity[attribute]}>
					<input type="text" placeholder={attribute}
						   defaultValue={this.props.employee.entity[attribute]}
						   ref={attribute} className="field" />
				</p>
		);

		var dialogId = "updateEmployee-" + this.props.employee.entity._links.self.href;

		return (
			<div key={this.props.employee.entity._links.self.href}>
				<a href={"#" + dialogId}>Update</a>
				<div id={dialogId} className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Update an employee</h2>

						<form>

							<input type="text" placeholder={"firstName"}
                            						   defaultValue={this.props.employee.entity["firstName"]}
                            						   ref={"firstName"} className="field" />
                            <input type="text" placeholder={"middleName"}
                                  					   defaultValue={this.props.employee.entity["middleName"]}
                                                   	   ref={"middleName"} className="field" />
                            <input type="text" placeholder={"lastName"}
                                                       defaultValue={this.props.employee.entity.lastName}
                               						   ref={"lastName"} className="field" />
                            <input type="text" placeholder={"email"}
                                                       defaultValue={this.props.employee.entity.email}
                                                       ref={"email"} className="field" />
                            <input type="text" placeholder={"employeeStatus"}
                                                       defaultValue={this.props.employee.entity.employeeStatus}
                                                       ref={"employeeStatus"} className="field" />

							<button onClick={this.handleSubmit}>Update</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

};
// end::update-dialog[]


class EmployeeList extends React.Component {

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
		this.handleSearchInput = this.handleSearchInput.bind(this);
	}

	// tag::handle-page-size-updates[]
	handleInput(e) {
		e.preventDefault();
		var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value = pageSize.substring(0, pageSize.length - 1);
		}
	}

	handleSearchInput(e) {
    	e.preventDefault();
    	var searchBox = ReactDOM.findDOMNode(this.refs.searchBox).value;
    	var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;

        this.props.updateSearchBox(searchBox,pageSize);


    	}
	// end::handle-page-size-updates[]

	// tag::handle-nav[]
	handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}
	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}
	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}
	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}
	// end::handle-nav[]
	// tag::employee-list-render[]
	render() {

		var employees = this.props.employees.map(employee =>
				<Employee key={employee.entity._links.self.href}
						  employee={employee}
						  attributes={this.props.attributes}
						  onUpdate={this.props.onUpdate}
						  onDelete={this.props.onDelete}/>
		);

		var navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (
			<div>
			    <div>
			        <div>
			            <table cellSpacing="0" cellPadding="0" style={{width: '100%'}}>
			                <tbody>
			                <tr>
                                <td style={{width: '100px', border: 'none'}}>Show</td>
                                <td style={{width: '100px', border: 'none'}}>
                                    <input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
                                </td>
                                <td style={{width: '100px',border: 'none'}}>entries</td>
                                <td style={{width: '100%',border: 'none'}}>
                                    <div style={{width: '100%'}} className="mv0xzc">
                                        <div className="g20x4k">
                                           <input ref="searchBox" className="searchInput" defaultValue={this.props.searchBox} id="searchBox" onInput={this.handleSearchInput} placeholder="Search by First or Last Name" />
                                        </div>
                                    </div>
                                </td>
                                <td style={{width: '100px', border: 'none'}}>
                                     <div style={{width: '100px', "zIndex": 2, border: 'none'}} className="searchButtonOutter">
                                           <button type="button" className="searchButton" ref="searchButton" onClick={this.props.handleSearchInput}>
                                                 <span>Search</span>
                                           </button>
                                      </div>
                                </td>
			                </tr>
			                </tbody>
			            </table>
			        </div>

                </div>

				<table id="employeesTable" style={{width: '100%'}} className="table table-striped">
					<tbody>
						<tr>
							<th>First Name</th>
							<th>Middle Name</th>
							<th>Last Name</th>
							<th>Email</th>
							<th>Job Title</th>
							<th>Salary</th>
							<th>Status</th>
							<th></th>
							<th></th>
						</tr>
						{employees}
					</tbody>
				</table>
				<div>
					{navLinks}
				</div>
			</div>
		)
	}
	// end::employee-list-render[]
}

// tag::employee[]
class Employee extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.employee);
	}

	render() {
		return (
			<tr>
				<td>{this.props.employee.entity.firstName}</td>
				<td>{this.props.employee.entity.middleName}</td>
				<td>{this.props.employee.entity.lastName}</td>
				<td>{this.props.employee.entity.email}</td>
				<td>{this.props.employee.entity.jobTitle}</td>
				<td>{this.props.employee.entity.salary}</td>
				<td>{this.props.employee.entity.employeeStatus}</td>
				<td>
					<UpdateDialog employee={this.props.employee}
								  attributes={this.props.attributes}
								  onUpdate={this.props.onUpdate}/>
				</td>
				<td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}
// end::employee[]

ReactDOM.render(
	<App />,
	document.getElementById('react')
)