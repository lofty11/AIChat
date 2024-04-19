import { validUsername, isExternal } from '@/utils/validate.js'

describe('Utils:validate', () => {
  it('validUsername', () => {
    expect(validUsername('admin')).toBe(true)
    expect(validUsername('editor')).toBe(true)
    expect(validUsername('xxxx')).toBe(false)
  })
  it('isExternal', () => {
    expect(isExternal('https://github.com/lofty11/AIChat')).toBe(true)
    expect(isExternal('github.com/PanJiaChen/vue-element-admin')).toBe(false)
    expect(isExternal('/aboutUs')).toBe(false)
    expect(isExternal('./aboutUs')).toBe(false)
    expect(isExternal('aboutUs')).toBe(false)
  })
})
