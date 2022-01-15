package in.koala.serviceImpl;

import in.koala.domain.DeviceToken;
import in.koala.enums.ErrorMessage;
import in.koala.exception.NonCriticalException;
import in.koala.mapper.DeviceTokenMapper;
import in.koala.service.DeviceTokenService;
import in.koala.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService {

    private final UserService userService;
    private final DeviceTokenMapper deviceTokenMapper;


    @Override
    public DeviceToken updateToken(String expiredToken, String newToken) {
        DeviceToken deviceToken = this.getDeviceTokenInfoByDeviceToken(expiredToken);

        deviceTokenMapper.updateExTokenToNewToken(expiredToken, newToken);

        deviceToken.setToken(newToken);

        return deviceToken;
    }

    @Override
    public void updateTokenTableUserId(Long normalUserId, String deviceToken) {
        DeviceToken deviceTokenInfo = this.getDeviceTokenInfoByDeviceToken(deviceToken);

        deviceTokenInfo.setUser_id(normalUserId);

        deviceTokenMapper.updateUserId(deviceTokenInfo);
    }

    @Override
    public DeviceToken getDeviceTokenInfoByDeviceToken(String deviceToken){
        return deviceTokenMapper.getTokenByDeviceToken(deviceToken)
                .orElseThrow(() -> new NonCriticalException(ErrorMessage.DEVICE_TOKEN_NOT_EXIST));
    }

    @Override
    public boolean checkTokenExist(String deviceToken) {
        return deviceTokenMapper.checkTokenExist(deviceToken) > 0;
    }

    @Override
    public void insertDeviceToken(DeviceToken deviceToken) {

        if(this.checkTokenExist(deviceToken.getToken())){
            throw new NonCriticalException(ErrorMessage.DEVICETOKEN_ALREADY_EXIST);
        }

        deviceTokenMapper.insertDeviceToken(deviceToken);
    }


}
