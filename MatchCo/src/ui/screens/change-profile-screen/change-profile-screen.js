// @flow

import glam from 'glamorous-native'
import React, { Component } from 'react'
import { View, Text, StyleSheet, TextInput, Dimensions } from 'react-native'
import { inject, observer } from 'mobx-react'
import { Form, StaticLayout } from '../..'
import type { UserStore } from '../../../stores/user-store'
import { translator } from '../../../services/translate/translator'
const { width, height } = Dimensions.get('window')

const Stretchy = glam.view({ height: height*.01 })

@translator
@inject('userStore')
@observer
export class ChangeProfileScreen extends Component {
  static propTypes: {
  };

  constructor(props) {
    super(props);
    // TODO: This should be taken from information already in the account.
    this.state = {
      firstName: '',
      lastName: '',
      email: '',
      phone: ''
    };
  }

  render () {
    const { userStore, translate } = this.props
    return (
      // TODO: The form isn't connected to anything. The information isn't being updated.
      <StaticLayout>
        <Text style={styles.title}>{translate('account.nameAndEmail')}</Text>
        <Form>
          <TextInput
            style={styles.text}
            autoCapitalize={'words'}
            placeholder={'Matthieu'} // <-- Will need to be taken from account
            onChangeText={(firstName) => this.setState({firstName})}
            value={this.state.firstName}
          /><Stretchy/>
          <TextInput
            style={styles.text}
            autoCapitalize={'words'}
            placeholder={'Rey'} // <-- Will need to be taken from account
            onChangeText={(lastName) => this.setState({lastName})}
            value={this.state.lastName}
          /><Stretchy/>
          <TextInput
            style={styles.text}
            autoCapitalize={'none'}
            placeholder={'matthieu@getmatch.co'} // <-- Will need to be taken from account
            onChangeText={(email) => this.setState({email})}
            value={this.state.email}
          /><Stretchy/>
        </Form>
        <Text style={styles.title}>{translate('account.phone')}</Text>
        <Form>
          <TextInput
            style={styles.text}
            autoCapitalize={'none'}
            placeholder={'123.456.7890'} // <-- Will need to be taken from account
            onChangeText={(phone) => this.setState({phone})}
            value={this.state.phone}
          /><Stretchy/>
        </Form>
      </StaticLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center'
  },
  title: { color: 'black', fontSize: 14, padding: 7 },
  text: { color: 'dimgray', fontSize: 12, height: height*.045, borderColor: '#dcb8bd', borderWidth: 2, padding: 7}
})
