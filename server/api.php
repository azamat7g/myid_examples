<?php

class API
{
    public function getUserToken($code)
    {
        $curl = curl_init();

        curl_setopt_array($curl, array(
            CURLOPT_URL => "https://myid.uz/api/v1/oauth2/access-token",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_CUSTOMREQUEST => "POST",
            CURLOPT_POSTFIELDS => http_build_query([
                'grant_type' => 'authorization_code',
                'code' => $code,
                'client_id' => YOUR_CLIENT_ID,
                'client_secret' => YOUR_CLIENT_SECRET,
            ]),
            CURLOPT_HTTPHEADER => [
                "content-type: application/x-www-form-urlencoded",
            ],
        ));

        $response = curl_exec($curl);
        $err = curl_error($curl);

        curl_close($curl);

        if ($err)
            $this->sendError(500, 'Unable to get User token');

        return $response;
    }

    public function getUserProfile($token)
    {
        $curl = curl_init();

        curl_setopt_array($curl, array(
            CURLOPT_URL => "https://myid.uz/api/v1/users/me",
            CURLOPT_HTTPHEADER => [
                "Authorization: Bearer $token",
            ],
        ));

        $response = curl_exec($curl);
        $err = curl_error($curl);

        curl_close($curl);

        if ($err)
            $this->sendError(500, 'Unable to get User data');

        return $response;
    }

    public function sendError($code, $message) {
        http_response_code($code);
        die($message);
    }
}

$api = new Api();

if (!isset($_GET['code']))
    $api->sendError(400, 'code is required');

$code = $_GET['code'];

$token = $api->getUserToken($code);

$json = json_decode($token, true);
if (empty(@$json['access_token']))
    $api->sendError(400, 'Invalid token');

$rez = $api->getUserProfile($json['access_token']);
return json_decode($rez);