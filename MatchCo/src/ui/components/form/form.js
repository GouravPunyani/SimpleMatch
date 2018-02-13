// @flow
import React, { Component } from 'react'
import { spacing } from '../../theme'
import glam from 'glamorous-native'

const Wrapper = glam.view({
  paddingVertical: spacing.extraSmall
})

export type Props = {
  children?: React.Children
};

export class Form extends Component<void, Props, void> {
  render () {
    return (
      <Wrapper>
        { this.props.children }
      </Wrapper>
    )
  }
}
